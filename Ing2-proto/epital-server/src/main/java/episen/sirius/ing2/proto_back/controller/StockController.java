 package episen.sirius.ing2.proto_back.controller;

import episen.sirius.ing2.proto_back.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

 @RestController
 @RequestMapping("/stock")
 @CrossOrigin(origins = "http://172.31.252.117:3000")
 public class StockController {

     @Autowired
     private StockService stockService;

     // Démarrer une sortie de stock
     @GetMapping("/simulate/once")
     public String simulateStockOnce() {
         return stockService.effectuerSortieUnique();
     }

     // Récupérer l'historique des sorties regroupé par médicament
     @GetMapping("/historique")
     public Map<String, List<Historique>> getHistoriqueOrdre() {
         return stockService.getHistoriqueRegroupe();
     }
 }