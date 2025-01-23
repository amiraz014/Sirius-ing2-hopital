package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.model.Historique;
import episen.sirius.ing2.proto_back.model.Stock;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.repository.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepo stockRepo;

    @Autowired
    private HistoriqueRepo historiqueRepo;

    public void effectuerSortie(Long medicamentId, Integer quantite) {
        Optional<Stock> stockOptional = stockRepo.findById(medicamentId);

        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            if (stock.getQuantite_disponible() >= quantite) {
                stock.setQuantite_disponible(stock.getQuantite_disponible() - quantite);

                // Enregistrer l'historique de la sortie
                Historique historique = new Historique();
                historique.setQuantite(quantite);
                historique.setDate_mouvement(new java.sql.Date(System.currentTimeMillis()));
                historique.setType("sortie");
                historique.setMedicament(stock.getMedicament()); // Assure-toi d'avoir la bonne relation
                historiqueRepo.save(historique);

                stockRepo.save(stock); // Sauvegarder les modifications dans le stock
            } else {
                throw new RuntimeException("Stock insuffisant pour effectuer la sortie.");
            }
        } else {
            throw new RuntimeException("Médicament non trouvé.");
        }
    }
}
