package episen.sirius.ing2.proto_back.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    // profession de garde du soir :
    private static final List<Long> PROFESSIONS_GARDE_SOIR = Arrays.asList(47L);

    public void planifierGardes(LocalDate debut, LocalDate fin) {
    
        List<Employe> EmployesGardeSoir = new ArrayList<>();
    

            EmployesGardeSoir.addAll(Erepo.findByProfessionId(PROFESSIONS_GARDE_SOIR.get(0)));
        

        if (EmployesGardeSoir.isEmpty()) {
            throw new RuntimeException("Aucun employé éligible pour les gardes de soir.");
        }
        
        List<String> typesDeGarde = Arrays.asList("MATIN", "SOIR");
        List<String> secteurs = Arrays.asList("Secteur A", "Secteur B", "Secteur C", "Secteur D", "Secteur E", "Secteur F");
        
        Map<Employe, Map<Integer, Integer>> compteurGardesParSemaine = new HashMap<>();
        
        Map<Employe, Set<LocalDate>> gardesEffectuees = new HashMap<>();

        for (Employe employe : EmployesGardeSoir) {
            compteurGardesParSemaine.put(employe, new HashMap<>());
            gardesEffectuees.put(employe, new HashSet<>());
        }

        LocalDate dateCourante = debut;

        while (!dateCourante.isAfter(fin)) {
            int semaineAnnee = dateCourante.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

            int gardesAttribuees = 0;

            while (gardesAttribuees < 12) { 
                for (String type : typesDeGarde) {
                    for (String secteur : secteurs) {

                        Employe employe = choisirEmploye(compteurGardesParSemaine, gardesEffectuees, EmployesGardeSoir, semaineAnnee, dateCourante, type);

                        if (employe == null) {
                            throw new RuntimeException("Aucun employé disponible pour la garde le " + dateCourante);
                        }

                        Garde garde = new Garde();
                        garde.setDate(dateCourante);
                        garde.setType(type);
                        garde.setHeure(getHeurePourType(type));
                        garde.setEmploye(employe);
                        Grepo.save(garde);

                        Lieu lieu = new Lieu();
                        lieu.setSecteur(secteur);
                       // lieu.setGarde(garde);
                        Lrepo.save(lieu);

                        gardesEffectuees.get(employe).add(dateCourante);
                        compteurGardesParSemaine.get(employe).put(semaineAnnee,
                                compteurGardesParSemaine.get(employe).getOrDefault(semaineAnnee, 0) + 1);

                        gardesAttribuees++;

                        // Sortir des boucles si on atteint la limite
                        if (gardesAttribuees >= 12) {
                            break;
                        }
                    }
                    if (gardesAttribuees >= 12) {
                        break;
                    }
                }
            }

            if (gardesAttribuees < 12) {
                throw new IllegalStateException("Le nombre minimum de gardes par jour n'est pas atteint: " + gardesAttribuees);
            }

            dateCourante = dateCourante.plusDays(1); 
        }

       
    }
    
    private Employe choisirEmploye(Map<Employe, Map<Integer, Integer>> compteurGardesParSemaine,
                                   Map<Employe, Set<LocalDate>> gardesEffectuees,
                                   List<Employe> employes, int semaineAnnee,
                                   LocalDate dateCourante, String type) {
        LocalDate date = dateCourante;

        return employes.stream()
                .filter(e -> !gardesEffectuees.get(e).contains(date)) 
                .filter(e -> compteurGardesParSemaine.get(e).getOrDefault(semaineAnnee, 0) < 3)
                .min(Comparator.comparingInt(e -> compteurGardesParSemaine.get(e).getOrDefault(semaineAnnee, 0)))
                .orElse(null);
    }

    private LocalTime getHeurePourType(String type) {
        return type.equals("MATIN") ? LocalTime.of(8, 0) : LocalTime.of(20, 0);
    }

}
