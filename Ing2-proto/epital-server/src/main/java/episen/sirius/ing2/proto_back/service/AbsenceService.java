package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.model.Absence;
import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Garde;
import episen.sirius.ing2.proto_back.repository.AbsenceRepo;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.GardeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;


@Service
public class AbsenceService {

    @Autowired
    private AbsenceRepo absenceRepo;
    @Autowired
    private EmployeRepo employeRepo;
    @Autowired
    private GardeRepo gardeRepo;
    //----------------------------------------------------------------------------------------------------
    public void saveAbsence(String motif, LocalDate dateAbsence, LocalTime timeAbsence, String username) {
        Absence absence = new Absence();
        Employe employe = employeRepo.findByNomUtilisateur(username);


        absence.setDate_absence(dateAbsence);
        absence.setTime_absence(timeAbsence);
        absence.setMotif(motif);
        absence.setEmploye(employe);
        absenceRepo.save(absence);

    }

    //----------------------------------------------------------------------------------------------------

    @Transactional
    public void UpdateGarde(LocalDate dateAbsence, LocalTime timeAbsence, String username) {

        Employe AbsentEmploye = employeRepo.findByNomUtilisateur(username);


        for(Garde garde : AbsentEmploye.getGardes()) {

            Employe employe =  employeRepo.findRandomly(40L, garde.getDate());
            gardeRepo.UpdateGarde(employe.getIdE(), garde.getIdG());
        }
    }





}