package episen.sirius.ing2.proto_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Garde;
import episen.sirius.ing2.proto_back.model.Lieu;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.GardeRepo;
import episen.sirius.ing2.proto_back.repository.LieuRepo;
import episen.sirius.ing2.proto_back.repository.ProfessionRepo;

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

    @Autowired
    private ProfessionRepo professionRepo;

    private static final List<String> GARDE_PROFESSIONS = Arrays.asList(
            "Médecin généraliste", "Radiologue", "Urgentiste", "Pharmacien",
            "Infirmier", "Sage-femme", "Ambulancier", "Chef de service"
    );

    public void planifierGardes(LocalDate dateDebut, LocalDate dateFin) {
        List<Employe> allEmployes = employeRepo.findAll();
        List<Employe> employesGarde = allEmployes.stream()
                .filter(e -> GARDE_PROFESSIONS.contains(e.getProfession().getNom()))
                .collect(Collectors.toList());

        if (employesGarde.isEmpty()) {
            throw new RuntimeException("Aucun employé éligible pour les gardes.");
        }

        LocalDate currentDate = dateDebut;
        Random random = new Random();

        while (!currentDate.isAfter(dateFin)) {
            // Planification de la journée
            planifierJournee(allEmployes, employesGarde, currentDate, random);
            currentDate = currentDate.plusDays(1);
        }
    }

    private void planifierJournee(List<Employe> allEmployes, List<Employe> employesGarde, LocalDate date, Random random) {
        // Planification du matin
        for (Employe employe : allEmployes) {
            Garde gardeMatin = new Garde();
            gardeMatin.setDate(date);
            gardeMatin.setType("Matin");
            gardeMatin.setHeure(LocalTime.of(8, 0));
            gardeMatin.setEmploye(employe);
            gardeRepo.save(gardeMatin);

            Lieu lieuMatin = new Lieu();
            lieuMatin.setSecteur("Matin");
            lieuMatin.setGarde(gardeMatin);
            lieuRepo.save(lieuMatin);
        }

        // Planification des gardes du soir
        Set<Employe> employesChoisis = new HashSet<>();
        while (employesChoisis.size() < Math.min(160, employesGarde.size())) {
            Employe employe = employesGarde.get(random.nextInt(employesGarde.size()));
            if (Collections.frequency(employesChoisis, employe) < 3) {
                employesChoisis.add(employe);
                Garde gardeSoir = new Garde();
                gardeSoir.setDate(date);
                gardeSoir.setType("Soir");
                gardeSoir.setHeure(LocalTime.of(20, 0));
                gardeSoir.setEmploye(employe);
                gardeRepo.save(gardeSoir);

                Lieu lieuSoir = new Lieu();
                lieuSoir.setSecteur("Soir");
                lieuSoir.setGarde(gardeSoir);
                lieuRepo.save(lieuSoir);
            }
        }
    }
}
