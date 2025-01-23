package episen.sirius.ing2.proto_back.controller;

import episen.sirius.ing2.proto_back.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // @GetMapping("/simulate")
    // public String simulateStock() {
    //     try {
    //        stockService.effectuerSortiesAleatoires();
    //         return "Simulation de sorties effectuée avec succès.";
    //     } catch (InterruptedException e) {
    //         Thread.currentThread().interrupt();
    //         return "Erreur lors de la simulation.";
    //     }
    // }
}
