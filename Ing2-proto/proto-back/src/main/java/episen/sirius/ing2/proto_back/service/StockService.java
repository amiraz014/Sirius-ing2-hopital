package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.model.Historique;
import episen.sirius.ing2.proto_back.model.Stock;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.repository.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class StockService {

    @Autowired
    private final StockRepo stockRepo;

    @Autowired
    private final HistoriqueRepo historiqueRepo;

    private final Random random = new Random();

    public StockService(StockRepo stockRepo, HistoriqueRepo historiqueRepo) {
        this.stockRepo = stockRepo;
        this.historiqueRepo = historiqueRepo;
    }

    // Méthode pour effectuer des sorties en continu sans simultanéité
    public void effectuerSortiesNonStop() throws InterruptedException {
        List<Stock> stocks = stockRepo.findAll();

        if (stocks.isEmpty()) {
            System.out.println("Aucun stock disponible.");
            return;
        }

        // Boucle infinie pour effectuer les sorties de manière continue
        while (true) {
            // Filtrer les médicaments en stock dont la quantité est supérieure à zéro
            List<Stock> stocksDisponibles = stocks.stream()
                    .filter(stock -> stock.getQuantite_disponible() > 0)
                    .toList();

            if (stocksDisponibles.isEmpty()) {
                System.out.println("Tous les stocks sont épuisés.");
                break;  // Terminer si tous les stocks sont épuisés
            }

            // Sélection aléatoire d'un médicament disponible en stock
            Stock stock = stocksDisponibles.get(random.nextInt(stocksDisponibles.size()));

            // Limitation de la quantité à sortir selon la quantité disponible
            int quantiteSortie = random.nextInt(stock.getQuantite_disponible()) + 1;

            // Mise à jour de la quantité en stock après la sortie
            stock.setQuantite_disponible(stock.getQuantite_disponible() - quantiteSortie);
            stockRepo.save(stock);  // Sauvegarde du nouveau stock

            // Enregistrement de la sortie dans l'historique
            Historique historique = new Historique();
            historique.setDate_mouvement(LocalDate.from(LocalDateTime.now())); // Date et heure actuelles
            historique.setQuantite(quantiteSortie); // Quantité sortie
            historique.setType("sortie"); // Type de mouvement ("sortie")
            historique.setMedicament(stock.getMedicament()); // Lien avec le médicament
            historiqueRepo.save(historique); // Sauvegarde dans l'historique

            // Affichage des résultats
            System.out.println("Sortie de " + quantiteSortie + " unités de " + stock.getMedicament().getNom() +
                    " (ID: " + stock.getMedicament().getIdM() + "), Quantité disponible après sortie: " + stock.getQuantite_disponible());

            // Délai aléatoire entre 5 et 15 secondes pour simuler l'attente naturelle entre les sorties
            TimeUnit.SECONDS.sleep(random.nextInt(11) + 5);
        }
    }
}
