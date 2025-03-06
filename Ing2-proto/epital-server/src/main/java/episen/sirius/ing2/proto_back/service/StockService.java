package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.model.Historique;
import episen.sirius.ing2.proto_back.model.Stock;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.repository.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class StockService {
    @Autowired
    private StockRepo stockRepo;

    @Autowired
    private HistoriqueRepo historiqueRepo;

    private final Random random = new Random();

    // Fonction pour une sortie unique de stock

    public String effectuerSortieUnique() {
        List<Stock> stockDisponible = stockRepo.findAll();
        List<Stock> filteredStock = new ArrayList<>();
        for (Stock stock : stockDisponible) {
            if (stock.getQuantite_disponible() > 0) {
                filteredStock.add(stock);
            }
        }

        if (filteredStock.isEmpty()) {
            return "🚨 Aucun stock disponible.";
        }

        // Je sélectionne un médicament aléatoire
        Stock stock = filteredStock.get(random.nextInt(filteredStock.size()));

        // Je retire une quantité aléatoire mais raisonnable
        int quantiteSortie = Math.min(random.nextInt(10) + 1, stock.getQuantite_disponible());

        // Mise à jour du stock
        stock.setQuantite_disponible(stock.getQuantite_disponible() - quantiteSortie);
        stockRepo.save(stock);

        // Enregistrement dans l'historique
        Historique historique = new Historique();
        historique.setMedicament(stock.getMedicament());
        historique.setQuantite(quantiteSortie);
        historique.setType("SORTIE");
        historique.setDate_mouvement(LocalDate.now());
        historiqueRepo.save(historique);

        return "✅ " + quantiteSortie + " unités de " + stock.getMedicament().getNom() +
                " retirées. Stock restant : " + stock.getQuantite_disponible();
    }

    // Fonction pour récupérer l'historique groupé par médicament
    public List<HistoriqueGroup> getHistoriqueRegroupe() {
        List<Historique> historiqueList = historiqueRepo.findAllOrderedByMedicament();
        List<HistoriqueGroup> groupedHistorique = new ArrayList<>();

        for (Historique historique : historiqueList) {
            boolean found = false;
            for (HistoriqueGroup group : groupedHistorique) {
                if (group.getMedicament().equals(historique.getMedicament().getNom())) {
                    group.getHistoriques().add(historique);
                    found = true;
                    break;
                }
            }
            if (!found) {
                HistoriqueGroup newGroup = new HistoriqueGroup();
                newGroup.setMedicament(historique.getMedicament().getNom());
                newGroup.setHistoriques(new ArrayList<>(Collections.singletonList(historique)));
                groupedHistorique.add(newGroup);
            }
        }
        return groupedHistorique;