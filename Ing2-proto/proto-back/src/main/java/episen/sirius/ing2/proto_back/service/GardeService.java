package episen.sirius.ing2.proto_back.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Garde;
import episen.sirius.ing2.proto_back.model.Lieu;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.GardeRepo;
import episen.sirius.ing2.proto_back.repository.LieuRepo;
import episen.sirius.ing2.proto_back.repository.ProfessionRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class GardeService {

    @Autowired
    private EmployeRepo employeRepo;
    
    @Autowired
    private GardeRepo gardeRepo;
    
    @Autowired
    private LieuRepo lieuRepo;
    
    @Autowired
    private ProfessionRepo professionRepo;

    // Liste des professions autorisées pour les gardes du soir
    private final List<String> PROFESSIONS_GARDE_SOIR = Arrays.asList(
        "Médecin généraliste", "Radiologue", "Urgentiste", "Pharmacien",
        "Infirmier", "Sage-femme", "Ambulancier", "Chef de service"
    );

    /**
     * Planifie les gardes pour une période donnée
     * @param dateDebut date de début de la période
     * @param dateFin date de fin de la période
     */
    @Transactional
    public void planifierGardes(LocalDate dateDebut, LocalDate dateFin) {
        List<Employe> tousLesEmployes = employeRepo.findAll();
        
        for (LocalDate date = dateDebut; !date.isAfter(dateFin); date = date.plusDays(1)) {
            // Planification des gardes du matin (tous les employés)
            planifierGardeMatin(date, tousLesEmployes);
            
            // Planification des gardes du soir (employés éligibles uniquement)
            List<Employe> employesEligiblesGardeSoir = tousLesEmployes.stream()
                .filter(this::estEligibleGardeSoir)
                .collect(Collectors.toList());
            planifierGardeSoir(date, employesEligiblesGardeSoir);
        }
    }

    private void planifierGardeMatin(LocalDate date, List<Employe> employes) {
        LocalTime heureMatin = LocalTime.of(8, 0);
        int nombreGardesRequises = 160;
        
        // Mélanger la liste des employés pour une distribution aléatoire
        List<Employe> employesMelanges = new ArrayList<>(employes);
        Collections.shuffle(employesMelanges);
        
        // Créer les gardes du matin
        for (int i = 0; i < nombreGardesRequises && i < employesMelanges.size(); i++) {
            Garde garde = new Garde();
            garde.setDate(date);
            garde.setType("Matin");
            garde.setHeure(heureMatin);
            garde.setEmploye(employesMelanges.get(i));
            
            Garde gardeSauvegardee = gardeRepo.save(garde);
            
            // Créer un lieu pour la garde
            Lieu lieu = new Lieu();
            lieu.setSecteur("Secteur " + (i % 10 + 1)); // Répartition en 10 secteurs
            lieu.setGarde(gardeSauvegardee);
            lieuRepo.save(lieu);
        }
    }

    private void planifierGardeSoir(LocalDate date, List<Employe> employesEligibles) {
        LocalTime heureSoir = LocalTime.of(20, 0);
        int nombreGardesRequises = 160;
        
        // Vérifier l'historique des gardes pour équilibrer la charge
        Map<Long, Integer> nombreGardesParEmploye = calculerNombreGardesParEmploye(date.minusDays(7), date);
        
        // Trier les employés par nombre de gardes (priorité à ceux qui en ont eu le moins)
        employesEligibles.sort((e1, e2) -> {
            int gardesE1 = nombreGardesParEmploye.getOrDefault(e1.getIdE(), 0);
            int gardesE2 = nombreGardesParEmploye.getOrDefault(e2.getIdE(), 0);
            return Integer.compare(gardesE1, gardesE2);
        });
        
        // Créer les gardes du soir
        for (int i = 0; i < nombreGardesRequises && i < employesEligibles.size(); i++) {
            Garde garde = new Garde();
            garde.setDate(date);
            garde.setType("Soir");
            garde.setHeure(heureSoir);
            garde.setEmploye(employesEligibles.get(i));
            
            Garde gardeSauvegardee = gardeRepo.save(garde);
            
            // Créer un lieu pour la garde
            Lieu lieu = new Lieu();
            lieu.setSecteur("Secteur " + (i % 10 + 1));
            lieu.setGarde(gardeSauvegardee);
            lieuRepo.save(lieu);
        }
    }

    private boolean estEligibleGardeSoir(Employe employe) {
        return PROFESSIONS_GARDE_SOIR.contains(employe.getProfession().getNom());
    }

    private Map<Long, Integer> calculerNombreGardesParEmploye(LocalDate debut, LocalDate fin) {
        // Cette méthode devrait être implémentée pour compter le nombre de gardes
        // par employé sur une période donnée
        Map<Long, Integer> nombreGardesParEmploye = new HashMap<>();
        List<Garde> gardes = gardeRepo.findAll(); // Idéalement, ajouter une méthode dans le repo pour filtrer par date
        
        for (Garde garde : gardes) {
            if (!garde.getDate().isBefore(debut) && !garde.getDate().isAfter(fin)) {
                Long idEmploye = garde.getEmploye().getIdE();
                nombreGardesParEmploye.merge(idEmploye, 1, Integer::sum);
            }
        }
        
        return nombreGardesParEmploye;
    }

    /**
     * Vérifie si un employé peut prendre une garde supplémentaire
     * @param employe l'employé à vérifier
     * @param date la date de la garde
     * @return true si l'employé peut prendre la garde
     */
    private boolean peutPrendreGarde(Employe employe, LocalDate date) {
        // Vérifier le nombre de gardes dans la semaine
        Map<Long, Integer> gardesSemaine = calculerNombreGardesParEmploye(
            date.minusDays(7),
            date
        );
        
        int nombreGardesSemaine = gardesSemaine.getOrDefault(employe.getIdE(), 0);
        return nombreGardesSemaine < 3; // Maximum 3 gardes par semaine
    }
}