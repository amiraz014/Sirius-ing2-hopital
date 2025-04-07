package episen.sirius.ing2.proto_back.controller;

import episen.sirius.ing2.proto_back.dto.AlerteStockDTO;
import episen.sirius.ing2.proto_back.dto.HistoriqueParMedicamentDTO;
import episen.sirius.ing2.proto_back.model.Historique;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private HistoriqueRepo historiqueRepo;

    @GetMapping("/lancer-sorties")
    public String lancerSortiesContinues() {
        stockService.lancerSortiesContinues();
        return "Démarrage des sorties continues...";
    }

    @GetMapping("/sorties")
    public List<String> getSorties() {
        return stockService.getSorties();
    }

    @GetMapping("/historique")
    public List<Historique> getHistorique() {
        return historiqueRepo.findAll();
    }

    @GetMapping("/historique/par-medicament")
    public List<HistoriqueParMedicamentDTO> getHistoriqueParMedicament() {
        return stockService.getHistoriqueGroupedByMedicament();
    }

    @GetMapping("/alertes")
    public List<AlerteStockDTO> getAlertesStock() {
        return stockService.getAlertesStock();
    }

    @PostMapping("/confirmer-commande/{medicamentId}")
    public ResponseEntity<String> confirmerCommande(@PathVariable Long medicamentId) {
        return stockService.confirmerCommande(medicamentId); }
}