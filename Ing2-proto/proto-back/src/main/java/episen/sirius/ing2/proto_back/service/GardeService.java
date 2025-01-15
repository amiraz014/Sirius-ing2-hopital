package episen.sirius.ing2.proto_back.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
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

        List<String> typesDeGarde = Arrays.asList("MATIN", "SOIR");
        List<String> secteurs = Arrays.asList("Secteur A", "Secteur B", "Secteur C");

        // Initialiser un compteur pour suivre le nombre de gardes par employé
        Map<Employe, Integer> compteurGardes = new HashMap<>();
        for (Employe employe : employes) {
            compteurGardes.put(employe, 0);
        }

        LocalDate dateCourante = debut;

        while (!dateCourante.isAfter(fin)) {
            for (String type : typesDeGarde) {
                for (String secteur : secteurs) {
                    Employe employe = choisirEmploye(compteurGardes, employes);

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

                    // Augmenter le compteur de gardes pour cet employé
                    compteurGardes.put(employe, compteurGardes.get(employe) + 1);
                }
            }
            dateCourante = dateCourante.plusDays(1);
        }

        // Vérifier que chaque employé a au moins 2 gardes par semaine
        validerGardesParSemaine(compteurGardes);
    }

    private Employe choisirEmploye(Map<Employe, Integer> compteurGardes, List<Employe> employes) {
        // Choisir l'employé avec le moins de gardes attribuées
        Employe employeChoisi = employes.stream()
            .min(Comparator.comparingInt(compteurGardes::get))
            .orElseThrow(() -> new IllegalArgumentException("Erreur dans la sélection de l'employé."));
        return employeChoisi;
    }

    private LocalTime getHeurePourType(String type) {
        if (type.equals("MATIN")) {
            return LocalTime.of(8, 0); 
        } else {
            return LocalTime.of(18, 0); 
        }
    }

    private void validerGardesParSemaine(Map<Employe, Integer> compteurGardes) {
        for (Map.Entry<Employe, Integer> entry : compteurGardes.entrySet()) {
            if (entry.getValue() < 2) {
                throw new IllegalStateException("L'employé " + entry.getKey().getNom() + " a moins de 2 gardes par semaine.");
            }
        }
    }
}
