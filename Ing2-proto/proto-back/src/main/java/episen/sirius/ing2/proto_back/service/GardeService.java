package episen.sirius.ing2.proto_back.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Garde;
import episen.sirius.ing2.proto_back.model.Lieu;
import episen.sirius.ing2.proto_back.model.Profession;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.GardeRepo;
import episen.sirius.ing2.proto_back.repository.LieuRepo;
import episen.sirius.ing2.proto_back.repository.ProfessionRepo;

@Service
public class GardeService {
    @Autowired
    private EmployeRepo Erepo;
    @Autowired
    private GardeRepo Grepo;
    @Autowired
    private LieuRepo Lrepo;
    @Autowired
    private ProfessionRepo Prepo;

    // Liste des professions qui peuvent avoir des gardes (utilisée pour la vérification)
    private static final List<String> PROFESSIONS_GARDE = Arrays.asList(
        "Médecin généraliste", "Radiologue", "Urgentiste", "Pharmacien",
        "Infirmier", "Sage-femme", "Ambulancier", "Chef de service"
    );

    public void planifierGardes(LocalDate debut, LocalDate fin) {
        // Récupérer toutes les professions de la base de données
        List<Profession> professions = Prepo.findAll();
        
        // Récupérer tous les employés
        List<Employe> tousLesEmployes = Erepo.findAll();
        
        // Filtrer les employés qui ont une profession autorisée pour les gardes
        List<Employe> employesGarde = tousLesEmployes.stream()
            .filter(e -> PROFESSIONS_GARDE.contains(e.getProfession().getNom()))
            .collect(Collectors.toList());

        if (employesGarde.isEmpty()) {
            throw new IllegalArgumentException("Aucun employé avec une profession autorisée pour les gardes n'a été trouvé.");
        }

        List<String> typesDeGarde = Arrays.asList("MATIN", "SOIR");
        List<String> secteurs = Arrays.asList("Secteur A", "Secteur B", "Secteur C");
        
        // Initialiser les compteurs et le suivi des dernières gardes
        Map<Employe, Integer> compteurGardes = new HashMap<>();
        Map<Employe, LocalDate> dernierGarde = new HashMap<>();
        Map<String, Integer> compteurGardesParProfession = new HashMap<>();
        
        employesGarde.forEach(e -> {
            compteurGardes.put(e, 0);
            dernierGarde.put(e, debut.minusDays(1));
            compteurGardesParProfession.put(e.getProfession().getNom(), 0);
        });

        LocalDate dateCourante = debut;
        while (!dateCourante.isAfter(fin)) {
            for (String type : typesDeGarde) {
                // Tous les employés peuvent faire des gardes du soir maintenant
                List<Employe> employesDisponibles = new ArrayList<>(employesGarde);

                for (String secteur : secteurs) {
                    // Choisir l'employé en tenant compte de plusieurs critères
                    Employe employe = choisirEmployeOptimal(
                        compteurGardes, 
                        dernierGarde,
                        compteurGardesParProfession,
                        employesDisponibles,
                        dateCourante,
                        type
                    );
                    
                    // Créer et sauvegarder la garde
                    Garde garde = new Garde();
                    garde.setDate(dateCourante);
                    garde.setType(type);
                    garde.setHeure(getHeurePourType(type));
                    garde.setEmploye(employe);
                    Grepo.save(garde);

                    // Créer et sauvegarder le lieu
                    Lieu lieu = new Lieu();
                    lieu.setSecteur(secteur);
                    lieu.setGarde(garde);
                    Lrepo.save(lieu);

                    // Mettre à jour les compteurs
                    compteurGardes.merge(employe, 1, Integer::sum);
                    dernierGarde.put(employe, dateCourante);
                    compteurGardesParProfession.merge(employe.getProfession().getNom(), 1, Integer::sum);
                }
            }
            dateCourante = dateCourante.plusDays(1);
        }

        validerDistributionGardes(compteurGardes, debut, fin);
    }

    private Employe choisirEmployeOptimal(
            Map<Employe, Integer> compteurGardes,
            Map<Employe, LocalDate> dernierGarde,
            Map<String, Integer> compteurGardesParProfession,
            List<Employe> employesDisponibles,
            LocalDate dateGarde,
            String typeGarde) {
        
        return employesDisponibles.stream()
            .filter(e -> {
                LocalDate derniereDate = dernierGarde.get(e);
                // Vérifier le temps de repos (au moins 24h)
                return derniereDate.plusDays(1).isBefore(dateGarde);
            })
            .min(Comparator
                // Privilégier les employés qui ont fait moins de gardes
                .comparingInt(compteurGardes::get)
                // Puis ceux dont la profession a fait moins de gardes
                .thenComparingInt(e -> compteurGardesParProfession.get(((Employe) e).getProfession().getNom()))
                // Enfin, ceux qui n'ont pas fait de garde depuis longtemps
                .thenComparing(e -> dernierGarde.get(e))
            )
            .orElseThrow(() -> new IllegalStateException(
                "Aucun employé disponible pour la garde du " + dateGarde + " (" + typeGarde + ")"
            ));
    }

    private LocalTime getHeurePourType(String type) {
        return type.equals("MATIN") ? LocalTime.of(8, 0) : LocalTime.of(20, 0);
    }

    private void validerDistributionGardes(
            Map<Employe, Integer> compteurGardes,
            LocalDate debut,
            LocalDate fin) {
        
        long nombreSemaines = Math.max(1, (fin.toEpochDay() - debut.toEpochDay()) / 7);
        int gardesMinimumParSemaine = 2;
        
        // Vérifier la distribution des gardes
        for (Map.Entry<Employe, Integer> entry : compteurGardes.entrySet()) {
            double moyenneGardesParSemaine = entry.getValue().doubleValue() / nombreSemaines;
            if (moyenneGardesParSemaine < gardesMinimumParSemaine) {
                throw new IllegalStateException(
                    String.format(
                        "L'employé %s (%s) a une moyenne de %.1f gardes par semaine, " +
                        "ce qui est inférieur au minimum requis de %d",
                        entry.getKey().getNom(),
                        entry.getKey().getProfession().getNom(),
                        moyenneGardesParSemaine,
                        gardesMinimumParSemaine
                    )
                );
            }
        }
    }
}