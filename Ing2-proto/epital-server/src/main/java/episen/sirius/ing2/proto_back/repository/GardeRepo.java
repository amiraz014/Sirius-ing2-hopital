package episen.sirius.ing2.proto_back.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Garde;

@Repository
public interface GardeRepo extends JpaRepository<Garde,Long> {

    boolean existsByEmployeAndDateAndType(Employe employe, LocalDate date, String type);

    boolean existsByEmployeAndDate(Employe employe, LocalDate date);

   

    
}
