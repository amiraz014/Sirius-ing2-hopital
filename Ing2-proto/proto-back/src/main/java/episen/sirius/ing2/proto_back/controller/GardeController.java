package episen.sirius.ing2.proto_back.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import episen.sirius.ing2.proto_back.service.GardeService;

@RestController
@RequestMapping("/epital-api")
public class GardeController {
    @Autowired
    private  GardeService gService;
    
        @GetMapping("/GardeAPI")
        public  void GardeAlgo() {
            LocalDate firstDay = LocalDate.of(2024, 9, 1);
            LocalDate LastDay = LocalDate.of(2024, 9, 3);

            gService.planifierGardes(firstDay, LastDay);
            
        
    }

}

