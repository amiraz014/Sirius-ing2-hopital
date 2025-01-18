package episen.sirius.ing2.proto_back.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
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

        // Initialiser un compteur pour suivre les gardes attribuées par employé par semaine
        Map<Employe, Map<Integer, Integer>> compteurGardesParSemaine = new HashMap<>();
        for (Employe employe : employes) {
            compteurGardesParSemaine.put(employe, new HashMap<>());
        }

        LocalDate dateCourante = debut;

        while (!dateCourante.isAfter(fin)) {
            int semaineAnnee = dateCourante.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

            for (String type : typesDeGarde) {
                for (String secteur : secteurs) {
                    // Sélectionner un employé en rotation
                    Employe employe = choisirEmployeAvecRepetition(compteurGardesParSemaine, employes, semaineAnnee);

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

                    // Mettre à jour le compteur pour cet employé
                    Map<Integer, Integer> gardesSemaine = compteurGardesParSemaine.get(employe);
                    gardesSemaine.put(semaineAnnee, gardesSemaine.getOrDefault(semaineAnnee, 0) + 1);
                }
            }
            dateCourante = dateCourante.plusDays(1);
        }

        // Validation finale pour s'assurer qu'aucun employé n'a moins de 2 gardes par semaine
        validerGardesParSemaine(compteurGardesParSemaine);
    }

    private Employe choisirEmployeAvecRepetition(Map<Employe, Map<Integer, Integer>> compteurGardesParSemaine,
                                                 List<Employe> employes, int semaineAnnee) {
        // Sélectionner l'employé ayant le moins de gardes pour équilibrer
        return employes.stream()
            .min(Comparator.comparingInt(e -> compteurGardesParSemaine.get(e).getOrDefault(semaineAnnee, 0)))
            .orElseThrow(() -> new IllegalArgumentException("Erreur dans la sélection de l'employé."));
    }

    private LocalTime getHeurePourType(String type) {
        if (type.equals("MATIN")) {
            return LocalTime.of(8, 0); // 08:00:00
        } else {
            return LocalTime.of(18, 0); // 18:00:00
        }
    }

    private void validerGardesParSemaine(Map<Employe, Map<Integer, Integer>> compteurGardesParSemaine) {
        for (Map.Entry<Employe, Map<Integer, Integer>> entry : compteurGardesParSemaine.entrySet()) {
            Employe employe = entry.getKey();
            Map<Integer, Integer> gardesSemaine = entry.getValue();

            for (Map.Entry<Integer, Integer> semaineEntry : gardesSemaine.entrySet()) {
                if (semaineEntry.getValue() < 2) {
                    throw new IllegalStateException(
                        "L'employé " + employe.getNom() + " a moins de 2 gardes pour la semaine " + semaineEntry.getKey() + ".");
                }
            }
        }
    }
}