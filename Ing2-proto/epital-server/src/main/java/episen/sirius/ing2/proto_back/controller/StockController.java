package episen.sirius.ing2.proto_back.controller;

import episen.sirius.ing2.proto_back.model.Historique;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import episen.sirius.ing2.proto_back.dto.HistoriqueParMedicamentDTO;


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
}