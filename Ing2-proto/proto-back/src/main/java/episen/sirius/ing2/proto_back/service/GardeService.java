package episen.sirius.ing2.proto_back.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Garde;
import episen.sirius.ing2.proto_back.model.Lieu;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.GardeRepo;
import episen.sirius.ing2.proto_back.repository.LieuRepo;

public class GardeService {
    @Autowired
    private EmployeRepo Erepo;
    @Autowired
    private GardeRepo Grepo;
    @Autowired
    private LieuRepo Lrepo;



      public void planifierGardes(LocalDate debut, LocalDate fin) {
        List<Employe> employes = Erepo.findAll();
        if (employes.isEmpty()) {
            throw new IllegalArgumentException("Aucun employé trouvé pour planifier les gardes.");
        }

        List<String> typesDeGarde = Arrays.asList("MATIN", "SOIR");
        List<String> secteurs = Arrays.asList("Secteur A", "Secteur B", "Secteur C");

        LocalDate dateCourante = debut;

        while (!dateCourante.isAfter(fin)) {
            for (String type : typesDeGarde) {
                for (String secteur : secteurs) {
                    Employe employe = choisirEmploye(employes);

                    Garde garde = new Garde();
                    garde.setDate(dateCourante);
                    garde.setType(type);
                    garde.setHeure(getHeurePourType(type));
                    garde.setEmploye(employe);

                    Grepo.save(garde);

                    Lieu lieu = new Lieu();
                    lieu.setSecteur(secteur);
                    lieu.setGarde(garde);

                    Lrepo.save(lieu);
                }
            }
            dateCourante = dateCourante.plusDays(1);
        }
    }



    private Employe choisirEmploye(List<Employe> employes) {
        Employe employeChoisi = employes.get(0);
        employes.add(employeChoisi);
        employes.remove(0);
        return employeChoisi;
    }

    private LocalTime getHeurePourType(String type) {
        if (type.equals("MATIN")) {
            return LocalTime.of(8, 0); // 08:00:00
        } else {
            return LocalTime.of(18, 0); // 18:00:00
        }
    }

    
}
