package episen.sirius.ing2.proto_back.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Profession;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.ProfessionRepo;

@Service
public class EmployeService {
    @Autowired
    private ProfessionRepo prepo;
    @Autowired
    private EmployeRepo erepo;

    String[] NOM_EMPLOYE = genererNomsEmployes();
    public static String[] genererNomsEmployes() {
        String[] noms = new String[2000];
        String[] prenomsExemple = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Hector", "Ivy", "Jack", 
                                   "Laura", "Max", "Nina", "Oscar", "Paul", "Quinn", "Rachel", "Sam", "Tina", "Victor"};
        String[] nomsExemple = {"Dupont", "Martin", "Bernard", "Durand", "Petit", "Lemoine", "Morel", "Fournier", "Girard", "Roux",
                                "Blanc", "Perrot", "Chauvet", "Garnier", "Barbier", "Chevalier", "Renaud", "Lopez", "Adam", "Fabre"};

        for (int i = 0; i < noms.length; i++) {
            String prenom = prenomsExemple[i % prenomsExemple.length];
            String nom = nomsExemple[i % nomsExemple.length];
            noms[i] = prenom + " " + nom;
        }
        return noms;
    }
     
     String [] NOM_PROFESSION = {
        "Médecin généraliste", "Chirurgien", "Anesthésiste", "Radiologue", "Cardiologue", "Pédiatre", "Psychiatre", "Neurologue",
        "Oncologue", "Urgentiste", "Dermatologue", "Gynécologue", "Orthopédiste", "Pharmacien", "Infirmier", "Aide-soignant",
        "Technicien de laboratoire", "Kinésithérapeute", "Ergothérapeute", "Nutritionniste", "Psychologue", "Logopède",
        "Sage-femme", "Ambulancier", "Technicien en imagerie médicale", "Biologiste médical", "Hématologue", "Endocrinologue",
        "Gastro-entérologue", "Infectiologue", "Néphrologue", "Ophtalmologue", "Oto-rhino-laryngologiste", "Pneumologue",
        "Rhumatologue", "Urologue", "Médecin du travail", "Chirurgien-dentiste", "Podologue", "Agent administratif",
        "Agent de stérilisation", "Technicien biomédical", "Ingénieur biomédical", "Préparateur en pharmacie", "Archiviste médical",
        "Technicien en maintenance hospitalière", "Agent de sécurité hospitalière", "Gestionnaire des ressources humaines",
        "Comptable hospitalier", "Responsable logistique", "Chef cuisinier", "Plombier hospitalier", "Électricien hospitalier",
        "Responsable informatique", "Technicien informatique", "Aumônier hospitalier", "Interprète médical", "Chercheur médical",
        "Assistant social", "Coordonnateur des soins", "Épidémiologiste", "Formateur en santé", "Hygiéniste hospitalier",
        "Orthophoniste", "Audioprothésiste", "Opticien", "Prothésiste dentaire", "Psychomotricien", "Agent d'accueil",
        "Chargé de communication", "Directeur médical", "Chef de service", "Responsable qualité", "Chef d'unité de soins",
        "Technicien en hygiène", "Agent de blanchisserie", "Agent de restauration", "Transporteur sanitaire", "Formateur clinique",
        "Inspecteur sanitaire", "Médecin légiste", "Spécialiste en médecine nucléaire", "Technicien en médecine nucléaire",
        "Préleveur en laboratoire", "Manipulateur en électroradiologie médicale", "Orthoptiste", "Conseiller en prévention santé",
        "Ingénieur sanitaire", "Responsable des achats hospitaliers", "Vétérinaire hospitalier", "Assistant vétérinaire",
        "Orthésiste", "Neurochirurgien", "Chirurgien plastique", "Chirurgien vasculaire", "Chef de projet en santé",
        "Infirmier en bloc opératoire", "Infirmier anesthésiste", "Infirmier en soins intensifs", "Infirmier de santé publique",
        "Infirmier scolaire", "Masseur-kinésithérapeute", "Physiothérapeute", "Chargé de recherche clinique", "Data scientist médical",
        "Expert en intelligence artificielle médicale", "Technicien en robotique chirurgicale", "Assistant en gériatrie",
        "Responsable des dons d'organes", "Coordonnateur de transplantation", "Responsable des risques hospitaliers",
        "Agent de nettoyage hospitalier", "Contrôleur de gestion hospitalière", "Juriste hospitalier", "Infirmier hygiéniste",
        "Spécialiste en médecine palliative", "Médecin de la douleur", "Spécialiste en soins palliatifs", "Chef de cuisine hospitalier",
        "Chef de salle en restauration", "Gestionnaire de matériel médical", "Technicien en équipements médicaux",
        "Directeur des opérations hospitalières", "Directeur des services économiques", "Spécialiste en ergonomie médicale",
        "Responsable des services sociaux", "Conseiller en éthique médicale", "Expert en sécurité sanitaire",
        "Responsable de la formation médicale", "Éducateur thérapeutique", "Responsable des relations publiques hospitalières",
        "Agent de gestion des déchets hospitaliers", "Gestionnaire des stocks hospitaliers", "Spécialiste en imagerie interventionnelle",
        "Spécialiste en radiologie interventionnelle", "Spécialiste en thérapie génique", "Responsable de la santé mentale",
        "Spécialiste en maladies rares", "Responsable des programmes de vaccination", "Spécialiste en santé environnementale",
        "Chirurgien thoracique", "Spécialiste en médecine régénérative"
     };






    


    public List<Profession> ProfessionMock(Integer nbProfession){
        List<Profession> professions = new ArrayList<>();
        Random random = new Random();
        for(int i  = 0; i <nbProfession; i++){
            String nom = NOM_PROFESSION[random.nextInt(NOM_PROFESSION.length)];
            Profession profession = new Profession();
            profession.setNom(nom);
        }
        return professions;

    }

    public List<Employe> EmployeMock(int nbEmploye){
        List<Employe> employes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i <nbEmploye; i++){
            String nom = NOM_EMPLOYE[random.nextInt(NOM_EMPLOYE.length)];
            Employe employe = new Employe();
            Profession profession = new Profession();
            employe.setNom(nom);
            employe.setAge(random.nextInt(80-25 + 1)+25);
            employe.setProfession(profession);
        }    
        return employes;
    }


    public void savedEmploye(){
        List<Employe> employes = EmployeMock(NOM_EMPLOYE.length);
        for(Employe employe : employes){
            erepo.save(employe);
        }
    }
    public void savedProfession(){
        List<Profession> professions = ProfessionMock(NOM_PROFESSION.length);
        for(Profession profession : professions){
            prepo.save(profession);
        }
    }
    
}
