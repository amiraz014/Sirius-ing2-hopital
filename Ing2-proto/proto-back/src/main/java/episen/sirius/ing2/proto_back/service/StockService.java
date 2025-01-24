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
 
   
    public String effectuerSortieUnique() {
        List<Stock> stocks = stockRepo.findAll();
 
        if (stocks.isEmpty()) {
            return "Aucun stock disponible.";
        }
 
      
        Stock stock = stocks.get(random.nextInt(stocks.size()));
 
        if (stock.getQuantite_disponible() > 0) {
            int quantiteSortie = random.nextInt(stock.getQuantite_disponible()) + 1; // Sortie entre 1 et stock dispo
 
           
            stock.setQuantite_disponible(stock.getQuantite_disponible() - quantiteSortie);
            stockRepo.save(stock);
 
          
            Historique historique = new Historique();
            historique.setDate_mouvement(LocalDate.from(LocalDateTime.now()));
            historique.setQuantite(quantiteSortie);
            historique.setType("sortie");
            historique.setMedicament(stock.getMedicament());
            historiqueRepo.save(historique);
 
           
            return "Sortie de " + quantiteSortie + " unités de " + stock.getMedicament().getNom() +
                    " (ID: " + stock.getMedicament().getIdM() + "), Quantité restante: " + stock.getQuantite_disponible();
        } else {
            return "Stock épuisé pour le médicament: " + stock.getMedicament().getNom();
        }
    }
}
