package episen.sirius.ing2.proto_back.repository;

import episen.sirius.ing2.proto_back.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {
    @Query("SELECT s FROM Stock s WHERE s.medicament.idm = :medicamentId")
    Optional<Stock> findByMedicamentIdm(Long medicamentId);
}