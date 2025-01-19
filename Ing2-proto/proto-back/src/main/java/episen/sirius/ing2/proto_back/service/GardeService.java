package episen.sirius.ing2.proto_back.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.*;
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

    private static final List<Long> PROFESSIONS_GARDE_SOIR = Arrays.asList(1L, 4L, 10L, 14L, 15L, 23L, 24L, 72L);

    public void planifierGardes(LocalDate debut, LocalDate fin) {
        List<Employe> NightGardEmployes = new ArrayList<>();

        for (Long professionId : PROFESSIONS_GARDE_SOIR) {
            NightGardEmployes.addAll(Erepo.findByProfessionId(professionId));
        }

        if (NightGardEmployes.isEmpty()) {
            throw new RuntimeException("Aucun employé éligible pour les gardes de soir.");
        }

        List<String> typesDeGarde = Arrays.asList("MATIN", "SOIR");
        List<String> secteurs = Arrays.asList("Secteur A", "Secteur B", "Secteur C", "Secteur D", "Secteur E", "Secteur F", "Secteur G");

        Map<Employe, Map<Integer, Integer>> compteurGardesParSemaine = new HashMap<>();
        Map<Employe, Set<LocalDate>> gardesEffectuees = new HashMap<>();

        for (Employe employe : NightGardEmployes) {
            compteurGardesParSemaine.put(employe, new HashMap<>());
            gardesEffectuees.put(employe, new HashSet<>());
        }

        LocalDate dateCourante = debut;

        while (!dateCourante.isAfter(fin)) {
            int semaineAnnee = dateCourante.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

            int gardesAttribuees = 0;
           
            while (gardesAttribuees < 160) {
                for (String type : typesDeGarde) {
                    for (String secteur : secteurs) {
                        

                        Employe employe = choisirEmploye(compteurGardesParSemaine, gardesEffectuees, NightGardEmployes, semaineAnnee, dateCourante, type);

                        if (employe == null) {
                            throw new RuntimeException("Aucun employé disponible pour la garde le " + dateCourante); //
                        }

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

                        gardesEffectuees.get(employe).add(dateCourante);
                        compteurGardesParSemaine.get(employe).put(semaineAnnee,
                                compteurGardesParSemaine.get(employe).getOrDefault(semaineAnnee, 0) + 1);

                        gardesAttribuees++;
                    }
                }
            }

            if (gardesAttribuees < 160) {
                throw new IllegalStateException("Le nombre minimum de gardes par jour n'est pas atteint: " + gardesAttribuees);
            }

             dateCourante.plusDays(1);
        }

        validerGardesParSemaine(compteurGardesParSemaine);
    }

    private Employe choisirEmploye(Map<Employe, Map<Integer, Integer>> compteurGardesParSemaine,
                                   Map<Employe, Set<LocalDate>> gardesEffectuees,
                                   List<Employe> employes, int semaineAnnee,
                                   LocalDate dateCourante, String type) {
        // Variable intermédiaire pour capturer dateCourante
        LocalDate currentDate = dateCourante;

        return employes.stream()
                .filter(e -> !gardesEffectuees.get(e).contains(currentDate))
                .filter(e -> compteurGardesParSemaine.get(e).getOrDefault(semaineAnnee, 0) < 3)
                // .filter(e -> gardesEffectuees.get(e).stream()
                //         .noneMatch(d -> d.equals(currentDate.minusDays(1)) && type.equals("MATIN")))
                .min(Comparator.comparingInt(e -> compteurGardesParSemaine.get(e).getOrDefault(semaineAnnee, 0)))
                .orElse(null);
    }

    private LocalTime getHeurePourType(String type) {
        return type.equals("MATIN") ? LocalTime.of(8, 0) : LocalTime.of(20, 0);
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
