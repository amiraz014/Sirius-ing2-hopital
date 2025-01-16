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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@AllArgsConstructor
public class GardeService {

    private final EmployeRepo employeRepo;
    private final GardeRepo gardeRepo;
    private final LieuRepo lieuRepo;
    private final ProfessionRepo professionRepo;

    @Transactional
    public void planifierGardes(LocalDate dateDebut, LocalDate dateFin) {
        List<String> professionsGardeSoir = Arrays.asList(
            "Médecin généraliste", "Radiologue", "Urgentiste", 
            "Pharmacien", "Infirmier", "Sage-femme", 
            "Ambulancier", "Chef de service"
        );

        // Récupérer tous les employés
        List<Employe> tousEmployes = employeRepo.findAll();
        
        // Filtrer les employés éligibles pour les gardes du soir
        List<Employe> employesGardeSoir = tousEmployes.stream()
            .filter(e -> professionsGardeSoir.contains(e.getProfession().getNom()))
            .collect(Collectors.toList());

        LocalDate date = dateDebut;
        while (!date.isAfter(dateFin)) {
            // Planification des gardes du matin (8h)
            planifierGardeMatin(date, tousEmployes);
            
            // Planification des gardes du soir (20h)
            planifierGardeSoir(date, employesGardeSoir);
            
            date = date.plusDays(1);
        }
    }

    private void planifierGardeMatin(LocalDate date, List<Employe> employes) {
        // Attribution aléatoire des gardes du matin
        Collections.shuffle(employes);
        int gardesNecessaires = 160;
        
        for (int i = 0; i < gardesNecessaires && i < employes.size(); i++) {
            Garde garde = new Garde();
            garde.setDate(date);
            garde.setType("Matin");
            garde.setHeure(LocalTime.of(8, 0));
            garde.setEmploye(employes.get(i));
            
            garde = gardeRepo.save(garde);
            
            // Création du lieu associé
            Lieu lieu = new Lieu();
            lieu.setSecteur("Secteur " + (i % 10 + 1)); // Distribution sur 10 secteurs
            lieu.setGarde(garde);
            lieuRepo.save(lieu);
        }
    }

    private void planifierGardeSoir(LocalDate date, List<Employe> employesEligibles) {
        // Vérification du nombre de gardes par employé sur la semaine précédente
        Map<Long, Integer> gardesParEmploye = new HashMap<>();
        
        LocalDate debutSemaine = date.minusDays(7);
        List<Garde> gardesSemaine = gardeRepo.findAll().stream()
            .filter(g -> !g.getDate().isBefore(debutSemaine) && g.getDate().isBefore(date))
            .filter(g -> g.getType().equals("Soir"))
            .collect(Collectors.toList());
            
        for (Garde g : gardesSemaine) {
            gardesParEmploye.merge(g.getEmploye().getIdE(), 1, Integer::sum);
        }
        
        // Filtrer les employés qui n'ont pas dépassé 3 gardes
        List<Employe> employesDisponibles = employesEligibles.stream()
            .filter(e -> gardesParEmploye.getOrDefault(e.getIdE(), 0) < 3)
            .collect(Collectors.toList());
            
        Collections.shuffle(employesDisponibles);
        
        int gardesNecessaires = 160;
        for (int i = 0; i < gardesNecessaires && i < employesDisponibles.size(); i++) {
            Garde garde = new Garde();
            garde.setDate(date);
            garde.setType("Soir");
            garde.setHeure(LocalTime.of(20, 0));
            garde.setEmploye(employesDisponibles.get(i));
            
            garde = gardeRepo.save(garde);
            
            // Création du lieu associé
            Lieu lieu = new Lieu();
            lieu.setSecteur("Secteur " + (i % 10 + 1));
            lieu.setGarde(garde);
            lieuRepo.save(lieu);
        }
    }

    // Méthode utilitaire pour vérifier la disponibilité d'un employé
    private boolean isEmployeDisponible(Employe employe, LocalDate date, String type) {
        // Vérifier si l'employé n'a pas déjà une garde ce jour-là
        return gardeRepo.findAll().stream()
            .filter(g -> g.getDate().equals(date))
            .filter(g -> g.getType().equals(type))
            .noneMatch(g -> g.getEmploye().getIdE().equals(employe.getIdE()));
    }
}