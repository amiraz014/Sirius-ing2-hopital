// package episen.sirius.ing2.proto_back.controller;

// import episen.sirius.ing2.proto_back.service.StockService;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;


// @RestController
// @RequestMapping("/stock")
// @CrossOrigin(origins = "http://172.31.252.117:3000")
// public class StockController {
//     @Autowired
//     private StockService stockService;    
//     @GetMapping("/simulate/once")
//     public String simulateStockOnce() { 
//                 return stockService.effectuerSortieUnique(); 
//             }
// }


// package episen.sirius.ing2.proto_back.controller;

// import episen.sirius.ing2.proto_back.service.StockService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/stock")
// @CrossOrigin("*") // Permet au front de faire des requêtes vers le back
// public class StockController {

//     @Autowired
//     private StockService stockService;

//     @PostMapping("/sortie")
//     public String sortieStock() {
//         new Thread(() -> {
//             try {
//                 stockService.effectuerSortiesNonStop();
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }).start();
//         return "Sortie de stock en cours...";
//     }
// }
