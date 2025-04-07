package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.dto.AlerteStockDTO;
import episen.sirius.ing2.proto_back.dto.HistoriqueParMedicamentDTO;
import episen.sirius.ing2.proto_back.model.Historique;
import episen.sirius.ing2.proto_back.model.Medicament;
import episen.sirius.ing2.proto_back.model.Stock;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.repository.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

                final List<Stock> stocksDisponibles = stocks.stream()
                        .filter(s -> s.getQuantite_disponible() > 0 && !"ÉPUISÉ".equals(s.getEtat()))
                        .collect(Collectors.toList());

                if (stocksDisponibles.isEmpty()) {
                    sorties.add("🔴 Aucun médicament disponible en stock. Arrêt du programme.");
                    break;
                }

                final Stock stock = stocksDisponibles.get(random.nextInt(stocksDisponibles.size()));

                final AtomicInteger quantiteSortie = new AtomicInteger(random.nextInt(100) + 1);

                effectuerSortieStock(stock.getIds(), quantiteSortie.get());

                try {
                    Thread.sleep(random.nextInt(15000) + 1000);
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

    private boolean effectuerSortieStock(Long medicamentId, Integer quantiteSortieParam) {
        if (quantiteSortieParam <= 0) return false;

        return stockRepo.findById(medicamentId).map(stock -> {

            final int adjustedQuantite = Math.min(quantiteSortieParam, stock.getQuantite_disponible());

            final int nouveauStock = stock.getQuantite_disponible() - adjustedQuantite;
            String etat = "NORMAL";
            String emoji = "✅";

            if (nouveauStock == 0) {
                etat = "ÉPUISÉ";
                emoji = "🔴";
            } else if (nouveauStock <= stock.getSeuil()) {
                etat = "CRITIQUE";
                emoji = "⚠";
            }

            stock.setEtat(etat);
            stock.setQuantite_disponible(nouveauStock);
            stockRepo.save(stock);

            Historique historique = new Historique();
            historique.setMedicament(stock.getMedicament());
            historique.setQuantite(adjustedQuantite);
            historique.setType("SORTIE");
            historique.setDate_mouvement(LocalDate.now());
            historiqueRepo.save(historique);

            String message = String.format("%s %d unités de %s (ID: %d) retirées. Stock %s: %d",
                    emoji, adjustedQuantite, stock.getMedicament().getNom(),
                    stock.getMedicament().getIdm(), etat.toLowerCase(), nouveauStock);

            sorties.add(message);
            return true;
        }).orElse(false);
    }

    public List<HistoriqueParMedicamentDTO> getHistoriqueGroupedByMedicament() {
        return historiqueRepo.findHistoriqueGroupedByMedicament().stream()
                .map(result -> new HistoriqueParMedicamentDTO(
                        (Long) result[0],
                        (String) result[1],
                        ((Number) result[2]).intValue(),
                        ((Number) result[3]).intValue(),
                        ((java.sql.Date) result[4]).toLocalDate()))
                .collect(Collectors.toList());
    }

    public List<AlerteStockDTO> getAlertesStock() {
        return stockRepo.findAll().stream()
                .filter(stock -> stock.getQuantite_disponible() <= stock.getSeuil())
                .map(this::convertToAlerteDTO)
                .collect(Collectors.toList());
    }

    private AlerteStockDTO convertToAlerteDTO(Stock stock) {
        AlerteStockDTO alerte = new AlerteStockDTO();
        alerte.setMedicamentId(stock.getMedicament().getIdm());
        alerte.setNomMedicament(stock.getMedicament().getNom());
        alerte.setStockActuel(stock.getQuantite_disponible());
        alerte.setSeuilCritique(stock.getSeuil());
        alerte.setQuantiteACommander(calculerQuantiteCommande(stock));
        alerte.setEtat(stock.getEtat());
        return alerte;
    }

    private int calculerQuantiteCommande(Stock stock) {
        List<Historique> recentSorties = historiqueRepo.findRecentSortiesByMedicament(stock.getMedicament().getIdm());

        if (recentSorties.isEmpty()) {
            return stock.getSeuil() * 2;
        }

        double sommePonderee = 0;
        double totalPoids = 0;

        for (int i = 0; i < recentSorties.size(); i++) {
            final double poids = 1.0 / (i + 1);
            sommePonderee += recentSorties.get(i).getQuantite() * poids;
            totalPoids += poids;
        }

        final double consommationMoyenne = sommePonderee / totalPoids;
        final int delaiLivraison = 7;

        return (int) Math.round(consommationMoyenne * delaiLivraison * 1.5);
    }

    public ResponseEntity<String> confirmerCommande(Long medicamentId) {
        Optional<Stock> stockOpt = stockRepo.findByMedicamentIdm(medicamentId);
        if (!stockOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Médicament non trouvé");
        }

        Stock stock = stockOpt.get();
        int quantite = calculerQuantiteCommande(stock);

        stock.setQuantite_disponible(stock.getQuantite_disponible() + quantite);
        stock.setEtat("NORMAL");
        stockRepo.save(stock);

        Historique historique = new Historique();
        historique.setMedicament(stock.getMedicament());
        historique.setQuantite(quantite);
        historique.setType("COMMANDE");
        historique.setDate_mouvement(LocalDate.now());
        historiqueRepo.save(historique);

        return ResponseEntity.ok(String.format(
                "Commande confirmée pour %d unités de %s (calculée automatiquement). Nouveau stock : %d",
                quantite, stock.getMedicament().getNom(), stock.getQuantite_disponible()
        ));
    }
}