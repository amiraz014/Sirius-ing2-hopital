package episen.sirius.ing2.proto_back.repository;

import episen.sirius.ing2.proto_back.model.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


import org.springframework.data.jpa.repository.Query;

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



}