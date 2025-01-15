package episen.sirius.ing2.proto_back.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Garde;
import episen.sirius.ing2.proto_back.model.Lieu;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.GardeRepo;
import episen.sirius.ing2.proto_back.repository.LieuRepo;

@Service
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

        List<String> typesDeGarde = List.of("MATIN", "APRES-MIDI", "NUIT");
        List<String> secteurs = List.of("Secteur A", "Secteur B", "Secteur C", "Secteur D", "Secteur E");

        // Initialiser un compteur pour suivre le nombre de gardes par employé
        Map<Employe, Integer> compteurGardes = new HashMap<>();
        for (Employe employe : employes) {
            compteurGardes.put(employe, 0);
        }

        LocalDate dateCourante = debut;
        int totalGardes = 0;

        while (!dateCourante.isAfter(fin) && totalGardes < 4500) {
            for (String type : typesDeGarde) {
                for (String secteur : secteurs) {
                    for (int i = 0; i < 3; i++) { // Ajouter plusieurs gardes par combinaison
                        Employe employe = choisirEmploye(compteurGardes, employes);

                        Garde garde = new Garde();
                        garde.setDate(dateCourante);
                        garde.setType(type);
                        garde.setHeure(getHeurePourType(type, i));
                        garde.setEmploye(employe);

                        Grepo.save(garde);

                        Lieu lieu = new Lieu();
                        lieu.setSecteur(secteur);
                        lieu.setGarde(garde);

                        Lrepo.save(lieu);

                        compteurGardes.put(employe, compteurGardes.get(employe) + 1);
                        totalGardes++;
                        if (totalGardes >= 4500) break; // Arrêter dès que l'objectif est atteint
                    }
                    if (totalGardes >= 4500) break;
                }
                if (totalGardes >= 4500) break;
            }
            dateCourante = dateCourante.plusDays(1);
        }

        System.out.println("Total des gardes planifiées : " + totalGardes);
    }

    private Employe choisirEmploye(Map<Employe, Integer> compteurGardes, List<Employe> employes) {
        // Choisir un employé au hasard
        return employes.get((int) (Math.random() * employes.size()));
    }

    private LocalTime getHeurePourType(String type, int index) {
        switch (type) {
            case "MATIN":
                return LocalTime.of(6 + index * 2, 0); // 06:00, 08:00, 10:00
            case "APRES-MIDI":
                return LocalTime.of(12 + index * 2, 0); // 12:00, 14:00, 16:00
            case "NUIT":
                return LocalTime.of(20 + index * 2, 0); // 20:00, 22:00, 00:00
            default:
                return LocalTime.of(0, 0); // Par défaut 00:00
        }
    }
}
