 package episen.sirius.ing2.proto_back.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import episen.sirius.ing2.proto_back.model.Employe;


@Repository
public interface EmployeRepo extends JpaRepository<Employe, Long> {

    @Query("SELECT e FROM Employe e WHERE e.profession.idP = :professionId")
    List<Employe> findByProfessionId(@Param("professionId") Long professionId);
    @Query("SELECT e FROM Employe e JOIN e.gardes g WHERE e.profession.idP = :professionId AND g.date = :dateAbsence AND g.heure = :timeAbsence ORDER BY RANDOM() LIMIT 1")
    Employe findRandomEmployeWithGarde(
            @Param("professionId") Long professionId,
            @Param("dateAbsence") LocalDate dateAbsence,
            @Param("timeAbsence") LocalTime timeAbsence
    );

    @Query("SELECT e FROM Employe e WHERE e.profession.idP = :professionId ORDER BY RANDOM() LIMIT 1")
    Employe findRandomly(@Param(("professionId")) Long professionId);
}
