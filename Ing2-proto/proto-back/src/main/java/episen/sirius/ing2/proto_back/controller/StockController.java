package episen.sirius.ing2.proto_back.controller;

import episen.sirius.ing2.proto_back.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/sortie/{medicamentId}/{quantite}")
    public String effectuerSortie(@PathVariable Long medicamentId, @PathVariable Integer quantite) {
        try {
            stockService.effectuerSortie(medicamentId, quantite);
            return "Sortie effectuée avec succès!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
