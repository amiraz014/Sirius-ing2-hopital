package episen.sirius.ing2.proto_back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.service.ViewService;

@RestController
@RequestMapping("/epital-api")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeController {

     @Autowired
     private ViewService viewService;
    @GetMapping("/employes")
    public ResponseEntity<List<Employe>> getEmployes() {
        List<Employe> employes = viewService.getAllEmployes();
        if (employes != null) {
            return new ResponseEntity<>(employes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(employes, HttpStatus.NOT_FOUND);
        }
    }


    // @GetMapping("/employes")
    // public ResponseEntity<List<Employe>> getEmployes() {
    //     List<Employe> employes = viewService.getEmployes();
    //     if (employes != null) {
    //         return new ResponseEntity<>(employes, HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(employes, HttpStatus.NOT_FOUND);
    //     }
    // }
}
