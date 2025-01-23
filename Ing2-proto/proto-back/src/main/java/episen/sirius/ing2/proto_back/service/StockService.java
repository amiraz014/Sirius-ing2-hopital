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

    public void effectuerSortiesAleatoires() throws InterruptedException {
        List<Stock> stocks = stockRepo.findAll();

        if (stocks.isEmpty()) {
            System.out.println("Aucun stock disponible.");
            return;
        }

        // On effectue entre 1 et 15 sorties aléatoires
        for (int i = 0; i < random.nextInt(15) + 1; i++) {
            // Sélection aléatoire d'un médicament en stock
            Stock stock = stocks.get(random.nextInt(stocks.size()));

            if (stock.getQuantite_disponible() > 0) {
                int quantiteSortie = random.nextInt(stock.getQuantite_disponible()) + 1; // Quantité à sortir entre 1 et la quantité disponible

                // Affichage avant la sortie
                System.out.println("Avant sortie: " + stock.getMedicament().getNom() + " (ID: " + stock.getMedicament().getIdM() +
                        "), Quantité disponible: " + stock.getQuantite_disponible());

                // Mise à jour de la quantité en stock après la sortie
                stock.setQuantite_disponible(stock.getQuantite_disponible() - quantiteSortie);
                stockRepo.save(stock);  // Sauvegarde du nouveau stock

                // Enregistrement de la sortie dans l'historique
                Historique historique = new Historique();
                historique.setDate_mouvement(LocalDate.from(LocalDateTime.now())); // Date et heure actuelles
                historique.setQuantite(quantiteSortie); // Quantité sortie
                historique.setType("sortie"); // Type de mouvement ("sortie")
                historique.setMedicament(stock.getMedicament()); // Lien avec le médicament
                // Tu peux ajouter ici l'employé si tu en as besoin (par exemple, stock.setEmployeIde(employeId))
                historiqueRepo.save(historique); // Sauvegarde dans l'historique

                // Affichage après la sortie
                System.out.println("Sortie de " + quantiteSortie + " unités de " + stock.getMedicament().getNom() +
                        " (ID: " + stock.getMedicament().getIdM() + "), Quantité disponible après sortie: " + stock.getQuantite_disponible());

                // Délai aléatoire entre 5 et 15 secondes pour simuler l'attente entre les sorties
                TimeUnit.SECONDS.sleep(random.nextInt(11) + 5);
            }
        }
    }
}
