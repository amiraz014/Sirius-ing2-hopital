package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.model.Historique;
import episen.sirius.ing2.proto_back.model.Stock;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.repository.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockService {
    @Autowired
    private StockRepo stockRepo;

    @Autowired
    private HistoriqueRepo historiqueRepo;

    private final Random random = new Random();
    private final Set<Long> medicamentsEpuises = new HashSet<>();

    public void effectuerSortiesNonStop() throws InterruptedException {
        System.out.println("\n📢 Début de la simulation des sorties de stock...\n");

        while (true) {
            // je dois charger les stocks disponibles
            List<Stock> stockDisponible = stockRepo.findAll().stream()
                    .filter(stock -> stock.getQuantite_disponible() > 0 && !medicamentsEpuises.contains(stock.getMedicament().getIdM()))
                    .collect(Collectors.toList());

            // Si aucun médicament disponible, j'arrete la boucle
            if (stockDisponible.isEmpty()) {
                System.out.println("\n✅ Tous les stocks sont épuisés. Arrêt de la simulation.");
                break;
            }

            // Je sélectionnes un médicament aléatoire
            Stock stock = stockDisponible.get(random.nextInt(stockDisponible.size()));

            // je determine une quantité de sortie raisonnable dans mon stock
            int quantiteSortie = Math.min(random.nextInt(10) + 1, stock.getQuantite_disponible());

            // je mets à jour la quantité disponible
            stock.setQuantite_disponible(stock.getQuantite_disponible() - quantiteSortie);
            stockRepo.save(stock);

            // J'enregistre dans l'historique ( il va m'aider apres pour calculer la moyenne de consommation )
            Historique historique = new Historique();
            historique.setMedicament(stock.getMedicament());
            historique.setQuantite(quantiteSortie);
            historique.setType("SORTIE");
            historique.setDate_mouvement(LocalDate.now());
            historiqueRepo.save(historique);

            //  J'affiche les résultats
            System.out.println("✅ Sortie de " + quantiteSortie + " unités de " + stock.getMedicament().getNom() +
                    " (ID: " + stock.getMedicament().getIdM() + "), Quantité restante: " + stock.getQuantite_disponible());

            // 🔹Je verifie les seuils et l'épuisement
            if (stock.getQuantite_disponible() < stock.getSeuil() && stock.getQuantite_disponible() > 0) {
                System.out.println("⚠️ Alerte: Stock de " + stock.getMedicament().getNom() +
                        " sous le seuil ! Quantité: " + stock.getQuantite_disponible() +
                        ", Seuil: " + stock.getSeuil());
            }

            if (stock.getQuantite_disponible() <= 0) {
                if (!medicamentsEpuises.contains(stock.getMedicament().getIdM())) {
                    System.out.println("❌ Stock épuisé pour le médicament: " + stock.getMedicament().getNom());
                    medicamentsEpuises.add(stock.getMedicament().getIdM());
                }
            }

            // Faire une pause pour simuler un délai entre les sorties
            Thread.sleep(2000);
        }

        System.out.println("\n📢 Fin de la simulation des sorties de stock.\n");
    }
}
