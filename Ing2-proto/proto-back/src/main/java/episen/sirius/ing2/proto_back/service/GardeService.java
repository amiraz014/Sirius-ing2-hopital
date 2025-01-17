package episen.sirius.ing2.proto_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Garde;
import episen.sirius.ing2.proto_back.model.Lieu;

import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.GardeRepo;
import episen.sirius.ing2.proto_back.repository.LieuRepo;

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

    
    private static final List<Long> PROFESSIONS_GARDE_SOIR = Arrays.asList( 1L,4L,10L,14L,15L,23L,24L,72L);

    @Transactional
    public void planifierGardes(LocalDate dateDebut, LocalDate dateFin) {
       // on récupere les données de la table employé :
        List<Employe> allEmployes = employeRepo.findAll();
        // Vérification si on a bien récupérer les données depuis la table :
        if (allEmployes.isEmpty()) {
            throw new RuntimeException("Aucun employé n'est disponible dans la base de données.");
        }
        
        for(Long id : PROFESSIONS_GARDE_SOIR){
            // On récupere les employés qui ont une id équivalente à ceux de la liste précédente(Professions_garde_soir)
        List<Employe> employesGardeSoir =  employeRepo.findByProfessionId(id); // notre probleme est ici vu que c'est vide
            // une Vérification si  on a bien récupéré...
        if (employesGardeSoir.isEmpty()) {
            throw new RuntimeException("Aucun employé éligible pour les gardes de soir.");
        }
        
        LocalDate currentDate = dateDebut;
        // si la date courrante est avant la date finale alors on planifie les journées:
        while (!currentDate.isAfter(dateFin)) {
            planifierJournee(allEmployes, employesGardeSoir, currentDate);
            currentDate = currentDate.plusDays(1);
        }
        }

        

       
    }

    @Transactional
    private void planifierJournee(List<Employe> allEmployes, List<Employe> employesGardeSoir, LocalDate date) {
        // Gardes du matin :


        for (Employe employe : allEmployes) {
            // Dans la matinée, tous les employés seront de service 
            Garde gardeMatin = new Garde();
            gardeMatin.setDate(date);
            gardeMatin.setType("Matin");
            gardeMatin.setHeure(LocalTime.of(8, 0));
            gardeMatin.setEmploye(employe);
            if(!gardeRepo.existsById(employe.getIdE())){
            Garde savedGarde = gardeRepo.save(gardeMatin);
            // On affecte un lieu de travail ici
            Lieu lieu = new Lieu();
            lieu.setSecteur("Secteur " + (new Random().nextInt(10) + 1));
            lieu.setGarde(savedGarde);
            lieuRepo.save(lieu);
            }
        }

        // Gardes du soir 
            // réviser un peu le Set et Collections (méthodes et fonctionnalités proposés)
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
                
                
                if(!gardeRepo.existsById(employe.getIdE())){
                    Garde  savedGarde = gardeRepo.save(gardeSoir);
                
                Lieu lieu = new Lieu();
                lieu.setSecteur("Secteur " + (new Random().nextInt(10) + 1));
                lieu.setGarde(savedGarde);
                lieuRepo.save(lieu);
                }
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