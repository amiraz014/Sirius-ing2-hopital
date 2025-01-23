package episen.sirius.ing2.proto_back.controller;

import episen.sirius.ing2.proto_back.model.Stock;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.repository.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void effectuerSortiesIntelligentes() throws InterruptedException {
        // Récupérer la liste des médicaments les plus utilisés
        List<Long> medicamentsPlusUtilises = historiqueRepo.findMostUsedMedicaments();

        if (medicamentsPlusUtilises.isEmpty()) {
            System.out.println("Aucune donnée d'utilisation trouvée.");
            return;
        }

        for (Long medicamentId : medicamentsPlusUtilises) {
            Stock stock = stockRepo.findByMedicamentIdM(medicamentId);

            if (stock != null && stock.getQuantite_disponible() > 0) {
                int quantiteSortie = random.nextInt(Math.max(stock.getQuantite_disponible() / 2, 1)) + 1;

                System.out.println("Avant sortie: " + stock.getMedicament().getNom() +
                        ", Quantité disponible: " + stock.getQuantite_disponible());

                stock.setQuantite_disponible(stock.getQuantite_disponible() - quantiteSortie);
                stockRepo.save(stock);

                System.out.println("Sortie de " + quantiteSortie + " unités de médicament: " +
                        stock.getMedicament().getNom() + ", Quantité disponible après sortie: " +
                        stock.getQuantite_disponible());

                // Ajouter un délai aléatoire
                TimeUnit.SECONDS.sleep(random.nextInt(11) + 5);
            }
        }
    }
}
