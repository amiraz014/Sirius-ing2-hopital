package episen.sirius.ing2.proto_back.repository;

import episen.sirius.ing2.proto_back.model.Historique;
import episen.sirius.ing2.proto_back.model.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueRepo extends JpaRepository<Historique, Long> {


    @Query(value = "SELECT m.idm AS idMedicament, m.nom AS nomMedicament, " +
            "COUNT(h.idh) AS nombreSorties, SUM(h.quantite) AS quantiteTotaleSortie, " +
            "MAX(h.date_mouvement) AS derniereSortie " +
            "FROM historique h " +
            "JOIN medicament m ON h.medicament_idm = m.idm " +
            "WHERE h.type = 'SORTIE' " +
            "GROUP BY m.idm, m.nom", nativeQuery = true)
    List<Object[]> findHistoriqueGroupedByMedicament();


    List<Historique> findByMedicament(Medicament medicament);


    @Query("SELECT h FROM Historique h WHERE h.medicament.idm = :medicamentId ORDER BY h.date_mouvement DESC")
    List<Historique> findByMedicamentId(Long medicamentId);


    @Query("SELECT h FROM Historique h " +
            "WHERE h.medicament.idm = :medicamentId " +
            "AND h.type = 'SORTIE' " +
            "AND h.date_mouvement >= CURRENT_DATE - 30 DAY " +
            "ORDER BY h.date_mouvement DESC")
    List<Historique> findRecentSortiesByMedicament(Long medicamentId);
}