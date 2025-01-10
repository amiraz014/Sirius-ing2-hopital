package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.model.*;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.repository.MedicamentRepo;
import episen.sirius.ing2.proto_back.repository.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MockService {
    @Autowired
    private MedicamentRepo mrepo;
    @Autowired
    private StockRepo srepo;
    @Autowired
    private HistoriqueRepo hrepo;

    private static final String[] MEDICAMENTS = {
            "Paracétamol 500mg", "Ibuprofène 200mg", "Adrénaline 1mg/mL", "Insuline 100UI/mL", "Salbutamol (Inhalateur)",
            "Oméprazole 20mg", "Furosémide 40mg", "Ceftriaxone 1g", "Morphine 10mg", "Loratadine 10mg",
            "Aspirine 500mg", "Amoxicilline 500mg", "Lévothyroxine 25mcg", "Prednisone 5mg", "Metformine 500mg",
            "Lisinopril 10mg", "Clonazépam 0.5mg", "Benzodiazépines", "Doxycycline 100mg", "Levofloxacine 500mg",
            "Atorvastatine 20mg", "Simvastatine 10mg", "Fentanyl 50mcg", "Hydrochlorothiazide 25mg", "Ranitidine 150mg"
    };

    public List<Medicament> creerMockMedicament(int nbMedicamente) {
        List<Medicament> medicaments = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < nbMedicamente; i++) {
            String nom = MEDICAMENTS[random.nextInt(MEDICAMENTS.length)];
            Medicament medicament = new Medicament();
            medicament.setNom(nom);
            medicament.setDescription("Description du médicament " + nom);
            medicaments.add(medicament);
        }
        return medicaments;
    }

    public List<Stock> creerMockStock(List<Medicament> medicaments, int nbStocks) {
        List<Stock> stocks = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < nbStocks; i++) {
            Stock stock = new Stock();
            stock.setMedicament(medicaments.get(random.nextInt(medicaments.size())));
            stock.setQuantite_disponible(random.nextInt(200) + 50); // Quantité entre 50 et 250
            stock.setSeuil(random.nextInt(50) + 20); // Seuil entre 20 et 70
            stock.setQuantite_reapprovisionnement(random.nextInt(100) + 50); // Quantité de réapprovisionnement
            stock.setEtat("En stock");
            stocks.add(stock);
        }
        return stocks;
    }

    public List<Historique> creerMockHistorique(List<Stock> stocks, int nbHistoriques) {
        List<Historique> historiques = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < nbHistoriques; i++) {
            Historique historique = new Historique();
            historique.setQuantite(random.nextInt(10) + 1); // Quantité entre 1 et 10
            historique.setDate_mouvement(new Date(System.currentTimeMillis())); // Date actuelle
            historique.setType(random.nextBoolean() ? "Entrée" : "Sortie"); // Type "Entrée" ou "Sortie"
            historique.setMedicament(stocks.get(random.nextInt(stocks.size())).getMedicament());
            historique.setEmploye(new Employe()); // Exemple de création d'un employé fictif, tu devras ajouter un employé réel ici
            historiques.add(historique);
        }
        return historiques;
    }
    public void savedMedicament() {
        List<Medicament> medicaments = creerMockMedicament(MEDICAMENTS.length);
            for (Medicament medicament: medicaments){
                mrepo.save(medicament);
            }
    }
    public void savedStock() {
        List<Stock> stocks = creerMockStock(creerMockMedicament(MEDICAMENTS.length),1000);
        for (Stock stock: stocks){
            srepo.save(stock);
        }
    }

    public void savedHistorique(){
            List<Historique> historiques = creerMockHistorique(creerMockStock(creerMockMedicament(MEDICAMENTS.length),1000), 1000);
            for (Historique historique: historiques){
                hrepo.save(historique);
            }
        }


}
