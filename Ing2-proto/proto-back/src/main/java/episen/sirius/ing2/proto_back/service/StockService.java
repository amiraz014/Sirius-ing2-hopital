package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.model.Stock;
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
    private final Random random = new Random();

    public StockService(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    public void effectuerSortiesAleatoires() throws InterruptedException {
        List<Stock> stocks = stockRepo.findAll();
        if (stocks.isEmpty()) {
            System.out.println("Aucun stock disponible.");
            return;
        }

        for (int i = 0; i < random.nextInt(10) + 1; i++) { // Effectuer entre 1 et 10 sorties aléatoires
            Stock stock = stocks.get(random.nextInt(stocks.size()));

            if (stock.getQuantite_disponible() > 0) {
                int quantiteSortie = random.nextInt(stock.getQuantite_disponible()) + 1;

                // Affichage avant la sortie
                System.out.println("Avant sortie: " + stock.getMedicament().getNom() + " (ID: " + stock.getMedicament().getIdM() +
                        "), Quantité disponible: " + stock.getQuantite_disponible());

                stock.setQuantite_disponible(stock.getQuantite_disponible() - quantiteSortie);
                stockRepo.save(stock);

                // Affichage après la sortie
                System.out.println("Sortie de " + quantiteSortie + " unités de médicament: " + stock.getMedicament().getNom() +
                        " (ID: " + stock.getMedicament().getIdM() + "), Quantité disponible après sortie: " + stock.getQuantite_disponible());

                // Délai aléatoire entre 5 et 15 secondes
                TimeUnit.SECONDS.sleep(random.nextInt(11) + 5);
            }
        }
    }
}
