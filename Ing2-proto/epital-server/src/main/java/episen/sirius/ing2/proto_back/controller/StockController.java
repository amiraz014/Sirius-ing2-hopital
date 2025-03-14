package episen.sirius.ing2.proto_back.controller;

import episen.sirius.ing2.proto_back.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    // Endpoint pour lancer les sorties continues
    @GetMapping("/lancer-sorties")
    public String lancerSortiesContinues() {
        new Thread(() -> stockService.lancerSortiesContinues()).start();
        return "Démarrage des sorties continues...";
    }
}