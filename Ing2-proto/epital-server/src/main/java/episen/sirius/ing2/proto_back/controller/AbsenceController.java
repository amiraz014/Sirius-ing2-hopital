package episen.sirius.ing2.proto_back.controller;

import episen.sirius.ing2.proto_back.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@CrossOrigin(origins = "http://172.31.252.117:3000")
@RequestMapping("/epital-api")
public class AbsenceController {
        @Autowired
        AbsenceService absenceService;
    @PostMapping("/addAbsence")
    public ResponseEntity<?> addAbsence(
            @RequestParam("AbsenceDate") String AbsenceDateStr,
            @RequestParam("AbsenceTime") String AbsenceTimeStr,
            @RequestParam("AbsenceReason") String motif,
            @RequestParam("username") String username
    ){
        try {
            LocalDate absenceDate = LocalDate.parse(AbsenceDateStr);
            LocalTime absenceTime = LocalTime.parse(AbsenceTimeStr);

            absenceService.saveAbsence(motif, absenceDate, absenceTime, username);
            absenceService.UpdateGarde(absenceDate,absenceTime);
            return ResponseEntity.ok("Planification réussie");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur de traitement : " + e.getMessage());
        }
    }
}