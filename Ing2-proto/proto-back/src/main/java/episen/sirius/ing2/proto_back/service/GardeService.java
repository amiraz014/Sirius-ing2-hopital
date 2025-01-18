package episen.sirius.ing2.proto_back.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.stream.Collectors;

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
    private static final int MINIMUM_GARDES_PAR_JOUR = 160;

    public void planifierGardes(LocalDate debut, LocalDate fin) {
        for(Long professionID : PROFESSIONS_GARDE_SOIR){
        List<Employe> employes = Erepo.findAll();
        if (employes.isEmpty()) {
            throw new IllegalArgumentException("Aucun employé trouvé pour planifier les gardes.");
        }

        List<Employe> nightGardEmployes = Erepo.findByProfessionId(professionID);

        if (nightGardEmployes.isEmpty()) {
            throw new RuntimeException("Aucun employé éligible pour les gardes de soir.");
        }

        List<String> typesDeGarde = Arrays.asList("MATIN", "SOIR");
        List<String> secteurs = Arrays.asList("Secteur A", "Secteur B", "Secteur C", "Secteur D", "Secteur E", "Secteur F", "Secteur G");

        Map<Employe, Map<Integer, Integer>> compteurGardesParSemaine = new HashMap<>();
        for (Employe employe : nightGardEmployes) {
            compteurGardesParSemaine.put(employe, new HashMap<>());
        }

        LocalDate dateCourante = debut;

        while (!dateCourante.isAfter(fin)) {
            int semaineAnnee = dateCourante.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            int totalGardesPourJour = 0;

            for (String type : typesDeGarde) {
                for (String secteur : secteurs) {
                    Employe employe = choisirEmployeAvecRepetition(compteurGardesParSemaine, nightGardEmployes, semaineAnnee);

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

                    Map<Integer, Integer> gardesSemaine = compteurGardesParSemaine.get(employe);
                    gardesSemaine.put(semaineAnnee, gardesSemaine.getOrDefault(semaineAnnee, 0) + 1);

                    totalGardesPourJour++;

                    if (totalGardesPourJour >= MINIMUM_GARDES_PAR_JOUR) {
                        break;
                    }
                }
                if (totalGardesPourJour >= MINIMUM_GARDES_PAR_JOUR) {
                    break;
                }
            }
            if (totalGardesPourJour < MINIMUM_GARDES_PAR_JOUR) {
                throw new IllegalStateException("Le nombre minimum de gardes par jour n'est pas atteint: " + totalGardesPourJour);
            }
            dateCourante = dateCourante.plusDays(1);
        }

        validerGardesParSemaine(compteurGardesParSemaine);
    }
}
    private Employe choisirEmployeAvecRepetition(Map<Employe, Map<Integer, Integer>> compteurGardesParSemaine,
                                                 List<Employe> employes, int semaineAnnee) {
        return employes.stream()
            .min(Comparator.comparingInt(e -> compteurGardesParSemaine.get(e).getOrDefault(semaineAnnee, 0)))
            .orElseThrow(() -> new IllegalArgumentException("Erreur dans la sélection de l'employé."));
    }

    private LocalTime getHeurePourType(String type) {
        return type.equals("MATIN") ? LocalTime.of(8, 0) : LocalTime.of(20, 0);
    }

    private void validerGardesParSemaine(Map<Employe, Map<Integer, Integer>> compteurGardesParSemaine) {
        for (Map.Entry<Employe, Map<Integer, Integer>> entry : compteurGardesParSemaine.entrySet()) {
            Employe employe = entry.getKey();
            Map<Integer, Integer> gardesSemaine = entry.getValue();

            for (Map.Entry<Integer, Integer> semaineEntry : gardesSemaine.entrySet()) {
                if (semaineEntry.getValue() < 3) {
                    throw new IllegalStateException(
                        "L'employé " + employe.getNom() + " a moins de 3 gardes pour la semaine " + semaineEntry.getKey() + ".");
                }
            }
        }
    }
}
