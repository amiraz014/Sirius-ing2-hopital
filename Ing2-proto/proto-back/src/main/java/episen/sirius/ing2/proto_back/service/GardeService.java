package episen.sirius.ing2.proto_back.service;

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
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GardeService {
    
    @Autowired
    private EmployeRepo employeRepo;
    
    @Autowired
    private GardeRepo gardeRepo;
    
    @Autowired
    private LieuRepo lieuRepo;

    
    private static final List<Long> PROFESSIONS_GARDE_SOIR = Arrays.asList(
        1L,4L,10L,14L,15L,23L,24L,72L
    );

    @Transactional
    public void planifierGardes(LocalDate dateDebut, LocalDate dateFin) {
       
        List<Employe> allEmployes = employeRepo.findAll();
        if (allEmployes.isEmpty()) {
            throw new RuntimeException("Aucun employé n'est disponible dans la base de données.");
        }

        for(Long id : PROFESSIONS_GARDE_SOIR){
        List<Employe> employesGardeSoir =  employeRepo.findByProfessionId(id);

        if (employesGardeSoir.isEmpty()) {
            throw new RuntimeException("Aucun employé éligible pour les gardes de soir.");
        }

        LocalDate currentDate = dateDebut;
        while (!currentDate.isAfter(dateFin)) {
            planifierJournee(allEmployes, employesGardeSoir, currentDate);
            currentDate = currentDate.plusDays(1);
        }
        }

        

       
    }

    @Transactional
    private void planifierJournee(List<Employe> allEmployes, List<Employe> employesGardeSoir, LocalDate date) {
        // Gardes du matin 
        for (Employe employe : allEmployes) {
            Garde gardeMatin = new Garde();
            gardeMatin.setDate(date);
            gardeMatin.setType("Matin");
            gardeMatin.setHeure(LocalTime.of(8, 0));
            gardeMatin.setEmploye(employe);
            
            Garde savedGarde = gardeRepo.save(gardeMatin);
            
            Lieu lieu = new Lieu();
            lieu.setSecteur("Secteur " + (new Random().nextInt(10) + 1));
            lieu.setGarde(savedGarde);
            lieuRepo.save(lieu);
        }

        // Gardes du soir 
        Set<Long> employesChoisis = new HashSet<>();
        int gardesNecessaires = 160;
        List<Employe> employesDisponibles = new ArrayList<>(employesGardeSoir);
        Collections.shuffle(employesDisponibles);

        int index = 0;
        while (gardesNecessaires > 0 && !employesDisponibles.isEmpty()) {
            Employe employe = employesDisponibles.get(index % employesDisponibles.size());
            if (employesChoisis.add(employe.getIdE())) {
                Garde gardeSoir = new Garde();
                gardeSoir.setDate(date);
                gardeSoir.setType("Soir");
                gardeSoir.setHeure(LocalTime.of(20, 0));
                gardeSoir.setEmploye(employe);
                
                Garde savedGarde = gardeRepo.save(gardeSoir);
                
                Lieu lieu = new Lieu();
                lieu.setSecteur("Secteur " + (new Random().nextInt(10) + 1));
                lieu.setGarde(savedGarde);
                lieuRepo.save(lieu);
                
                gardesNecessaires--;
            }
            index++;
            
           
            if (index >= employesDisponibles.size() * 3) {
                employesChoisis.clear(); 
                index = 0;
                Collections.shuffle(employesDisponibles);
            }
        }
    }

    // Méthode pour vérifier les gardes planifiées
    public List<Garde> getGardesByDate(LocalDate date) {
        return gardeRepo.findAll().stream()
                .filter(garde -> garde.getDate().equals(date))
                .collect(Collectors.toList());
    }
}