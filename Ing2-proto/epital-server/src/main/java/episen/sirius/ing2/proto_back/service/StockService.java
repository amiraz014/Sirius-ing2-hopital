package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.model.Historique;
import episen.sirius.ing2.proto_back.model.Stock;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.repository.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import episen.sirius.ing2.proto_back.dto.HistoriqueParMedicamentDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StockService {

    @Autowired
    private StockRepo stockRepo;

    @Autowired
    private HistoriqueRepo historiqueRepo;

    private final Random random = new Random();
    private final List<String> sorties = new ArrayList<>();


    public void lancerSortiesContinues() {
        new Thread(() -> {
            while (true) {
                List<Stock> stocks = stockRepo.findAll();

                // Sélectionner uniquement les stocks disponibles (quantité > 0 et état != ÉPUISÉ)
                List<Stock> stocksDisponibles = new ArrayList<>();
                for (Stock stock : stocks) {
                    if (stock.getQuantite_disponible() > 0 && !"ÉPUISÉ".equals(stock.getEtat())) {
                        stocksDisponibles.add(stock);
                    }
                }

                // Si aucun stock disponible, arrêter le programme
                if (stocksDisponibles.isEmpty()) {
                    sorties.add("🔴 Aucun médicament disponible en stock. Arrêt du programme.");
                    break;
                }

                // Sélectionner un médicament aléatoire parmi les stocks disponibles
                Stock stock = stocksDisponibles.get(random.nextInt(stocksDisponibles.size()));

                // Définir une quantité de sortie aléatoire
                int quantiteSortie = random.nextInt(60) + 1;

                boolean sortieEffectuee = effectuerSortieStock(stock.getIds(), quantiteSortie);
                if (sortieEffectuee) {
                    // Le message est maintenant généré dans la méthode effectuerSortieStock
                }


                try {
                    int delai = random.nextInt(8000) + 1000; // Entre 1 et 5 secondes
                    Thread.sleep(delai);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }


    public List<String> getSorties() {
        return sorties;
    }


    private boolean effectuerSortieStock(Long medicamentId, Integer quantiteSortie) {
        if (quantiteSortie <= 0) {
            return false;
        }

        Stock stock = stockRepo.findById(medicamentId).orElse(null);
        if (stock == null) {
            return false;
        }

        if (stock.getQuantite_disponible() < quantiteSortie) {
            quantiteSortie = stock.getQuantite_disponible(); // On retire uniquement la quantité disponible
        }


        int nouveauStock = stock.getQuantite_disponible() - quantiteSortie;


        if (nouveauStock == 0) {
            stock.setEtat("ÉPUISÉ");
            stock.setQuantite_disponible(0);
            stockRepo.save(stock);

            // Enregistrement historique
            Historique historique = new Historique();
            historique.setMedicament(stock.getMedicament());
            historique.setQuantite(quantiteSortie);
            historique.setType("SORTIE");
            historique.setDate_mouvement(LocalDate.now());
            historiqueRepo.save(historique);


            String message = "🔴 " + quantiteSortie + " unités de " + stock.getMedicament().getNom() +
                    " (ID: " + stock.getMedicament().getIdm() + ") retirées. Stock épuisé.";
            sorties.add(message);
            System.out.println(message);

            return true;
        } else if (nouveauStock <= stock.getSeuil()) {
            stock.setEtat("CRITIQUE");
            stock.setQuantite_disponible(nouveauStock);
            stockRepo.save(stock);

            // Enregistrement historique
            Historique historique = new Historique();
            historique.setMedicament(stock.getMedicament());
            historique.setQuantite(quantiteSortie);
            historique.setType("SORTIE");
            historique.setDate_mouvement(LocalDate.now());
            historiqueRepo.save(historique);


            String message = "⚠️ " + quantiteSortie + " unités de " + stock.getMedicament().getNom() +
                    " (ID: " + stock.getMedicament().getIdm() + ") retirées. Stock critique : " + nouveauStock;
            sorties.add(message);
            System.out.println(message);

            return true;
        } else {
            stock.setEtat("NORMAL");
            stock.setQuantite_disponible(nouveauStock);
            stockRepo.save(stock);


            Historique historique = new Historique();
            historique.setMedicament(stock.getMedicament());
            historique.setQuantite(quantiteSortie);
            historique.setType("SORTIE");
            historique.setDate_mouvement(LocalDate.now());
            historiqueRepo.save(historique);


            String message = "✅ " + quantiteSortie + " unités de " + stock.getMedicament().getNom() +
                    " (ID: " + stock.getMedicament().getIdm() + ") retirées. Stock restant : " + nouveauStock;
            sorties.add(message);
            System.out.println(message);

            return true;
        }
    }

    public List<HistoriqueParMedicamentDTO> getHistoriqueGroupedByMedicament() {
        List<Object[]> results = historiqueRepo.findHistoriqueGroupedByMedicament();
        List<HistoriqueParMedicamentDTO> historiqueParMedicament = new ArrayList<>();

        for (Object[] result : results) {
            Long idMedicament = (Long) result[0];
            String nomMedicament = (String) result[1];
            Integer nombreSorties = ((Number) result[2]).intValue();
            Integer quantiteTotaleSortie = ((Number) result[3]).intValue();
            LocalDate derniereSortie = ((java.sql.Date) result[4]).toLocalDate();

            historiqueParMedicament.add(new HistoriqueParMedicamentDTO(
                    idMedicament, nomMedicament, nombreSorties, quantiteTotaleSortie, derniereSortie
            ));
        }

        return historiqueParMedicament;
    }
}




