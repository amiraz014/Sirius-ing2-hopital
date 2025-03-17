package episen.sirius.ing2.proto_back.repository;

import episen.sirius.ing2.proto_back.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AbsenceRepo extends JpaRepository<Absence, Long> {

}
