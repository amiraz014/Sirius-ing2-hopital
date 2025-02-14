package episen.sirius.ing2.proto_back.repository;

import episen.sirius.ing2.proto_back.model.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueRepo extends JpaRepository<Historique, Long> {
    @Query("SELECT h.medicament.idM FROM Historique h WHERE h.type = 'SORTIE' GROUP BY h.medicament.idM ORDER BY SUM(h.quantite) DESC")
    List<Long> findMostUsedMedicaments();
}
