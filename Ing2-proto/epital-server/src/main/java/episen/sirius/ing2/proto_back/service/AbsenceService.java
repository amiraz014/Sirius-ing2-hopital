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
    public Boolean UpdateGarde(LocalDate dateAbsence, LocalTime timeAbsence) {
        Employe employe = employeRepo.findRandomly(40L, dateAbsence);
        if (isEligibleForGarde(employe, dateAbsence, timeAbsence)) {
            for(Absence absence : absenceRepo.findAll()) {
                for(Garde g : absence.getEmploye().getGardes()) {
                    Integer rows = gardeRepo.UpdateGarde(employe.getIdE(), dateAbsence, timeAbsence, g.getIdG());
                    return rows > 0;
                }
                }
        }
        return false;
    }

    public boolean isEligibleForGarde(Employe employe,LocalDate dateAbsence, LocalTime timeAbsence) {
            for (Garde garde : employe.getGardes()) {
                if(garde.getDate().equals(dateAbsence) && garde.getHeure().equals(timeAbsence)){
                    return false;
                }
            }
            for (Absence absence : employe.getAbsences()) {
                if (absence.getDate_absence().equals(dateAbsence) && absence.getTime_absence().equals(timeAbsence)) {
                    return false;
                }
            }
            return true;
    }




}
