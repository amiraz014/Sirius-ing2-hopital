package episen.sirius.ing2.proto_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import episen.sirius.ing2.proto_back.model.Employe;

@Repository
public interface EmployeRepo extends JpaRepository<Employe, Long> {

    @Query("SELECT e FROM Employe e WHERE e.profession.id = :professionId")
    List<Employe> findByProfessionId(@Param("professionId") Long professionId);
    
    @Query("SELECT e.nom, p.nom, g.date, g.heure, g.type, l.secteur " +
    "FROM Employe e " +
    "JOIN e.profession p " +
    "JOIN e.gardes g " +
    "JOIN g.lieu l " +
    "WHERE e.profession.id = :professionId")
    List<Employe> findEmployeDetailsByProfessionId(@Param("professionId") Long professionId);
    
}
