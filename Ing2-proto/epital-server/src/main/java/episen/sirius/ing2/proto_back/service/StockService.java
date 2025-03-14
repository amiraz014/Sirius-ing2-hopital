package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.model.Historique;
import episen.sirius.ing2.proto_back.model.Stock;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.repository.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Effectuer une sortie de stock en temps réel
    public boolean effectuerSortieStock(Long medicamentId, Integer quantiteSortie) {
        // Vérifier que la quantité de sortie est positive
        if (quantiteSortie <= 0) {
            return false; // Quantité invalide
        }

        // Trouver le stock correspondant au médicament
        Stock stock = stockRepo.findById(medicamentId).orElse(null);
        if (stock == null) {
            return false; // Médicament non trouvé
        }

        // Vérifier si la quantité disponible est suffisante
        if (stock.getQuantite_disponible() < quantiteSortie) {
            return false; // Quantité insuffisante
        }

        // Mettre à jour le stock
        stock.setQuantite_disponible(stock.getQuantite_disponible() - quantiteSortie);
        stockRepo.save(stock);

        // Enregistrer la sortie dans l'historique
        Historique historique = new Historique();
        historique.setMedicament(stock.getMedicament());
        historique.setQuantite(quantiteSortie);
        historique.setType("SORTIE");
        historique.setDate_mouvement(LocalDate.now());
        historiqueRepo.save(historique);

        return true; // Sortie effectuée avec succès
    }

    // Lancer des sorties continues
    public void lancerSortiesContinues() {
        while (true) {
            // Récupérer tous les stocks disponibles
            List<Stock> stocks = stockRepo.findAll();

            // Vérifier si le stock est totalement épuisé
            boolean stockEpuise = true;
            for (Stock stock : stocks) {
                if (stock.getQuantite_disponible() > 0) {
                    stockEpuise = false;
                    break;
                }
            }

            if (stockEpuise) {
                System.out.println("🔴 Le stock est totalement épuisé. Arrêt du programme.");
                break;
            }

            // Sélectionner un médicament aléatoire avec du stock disponible
            List<Stock> stocksDisponibles = new ArrayList<>();
            for (Stock stock : stocks) {
                if (stock.getQuantite_disponible() > 0) {
                    stocksDisponibles.add(stock);
                }
            }

            if (stocksDisponibles.isEmpty()) {
                System.out.println("🔴 Aucun médicament disponible en stock. Arrêt du programme.");
                break;
            }

            Stock stock = stocksDisponibles.get(random.nextInt(stocksDisponibles.size()));

            // Définir une quantité de sortie aléatoire (entre 1 et 10 unités)
            int quantiteSortie = random.nextInt(10) + 1;

            // Effectuer la sortie
            boolean sortieEffectuee = effectuerSortieStock(stock.getIds(), quantiteSortie);
            if (sortieEffectuee) {
                System.out.println("✅ " + quantiteSortie + " unités de " + stock.getMedicament().getNom() +
                        " retirées. Stock restant : " + stock.getQuantite_disponible());
            }

            // Attendre un décalage aléatoire entre 1 et 5 secondes
            try {
                int delai = random.nextInt(5000) + 1000; // Entre 1 et 5 secondes
                Thread.sleep(delai);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}