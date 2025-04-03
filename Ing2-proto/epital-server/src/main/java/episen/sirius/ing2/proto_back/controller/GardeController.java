package episen.sirius.ing2.proto_back.controller;


import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import episen.sirius.ing2.proto_back.service.AbsenceService;
import episen.sirius.ing2.proto_back.service.GardeService;

@RestController
@RequestMapping("/epital-api")
@CrossOrigin(origins = "http://172.31.250.115:3000")
public class GardeController {
    @Autowired
    private  GardeService gService;
<<<<<<< HEAD
  
    
=======
    @Autowired
    private AbsenceService absenceService;
        //---------------------------------------------------------------------------------------
>>>>>>> prod
        @GetMapping("/GardeAPI")
        public  void GardeAlgo() {
            LocalDate firstDay = LocalDate.of(2024, 9, 1);
            LocalDate LastDay = LocalDate.of(2024, 10, 1);

            gService.planifierGardes(firstDay, LastDay);
        }
        //---------------------------------------------------------------------------------------
      @PostMapping("/frontData")
      public ResponseEntity<?> addGard(
    @RequestParam("startDate") String startDateStr, 
    @RequestParam("endDate") String endDateStr ) {
        try {
             LocalDate startDate = LocalDate.parse(startDateStr);
             LocalDate endDate = LocalDate.parse(endDateStr);
        
             gService.planifierGardes(startDate, endDate);
        
             return ResponseEntity.ok("Planification réussie");
            } catch (Exception e) {
             return ResponseEntity.badRequest().body("Erreur de traitement : " + e.getMessage());
            }
        }
        //---------------------------------------------------------------------------------------
        @PostMapping("/addAbsence")
        public ResponseEntity<?> addAbsence(
            @RequestParam("AbsenceReason") String motif,
            @RequestParam("AbsenceDate") String date_absenceStr,
            @RequestParam("AbsenceTime") String time_absenceStr)
        {
            try {
                LocalDate date_absence = LocalDate.parse(date_absenceStr);
                LocalTime time_absence = LocalTime.parse(time_absenceStr);
                absenceService.saveAbsence(motif, date_absence, time_absence);
                absenceService.UpdateGarde(date_absence, time_absence);
                return ResponseEntity.ok("Absence ajoutée");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Erreur de traitement : " + e.getMessage());
            }

        }

       }


