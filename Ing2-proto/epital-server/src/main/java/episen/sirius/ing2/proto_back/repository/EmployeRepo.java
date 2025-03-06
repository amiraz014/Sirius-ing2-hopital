

package episen.sirius.ing2.proto_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import episen.sirius.ing2.proto_back.model.Employe;
import main.java.episen.sirius.ing2.proto_back.model.EmployeDTO;
@Repository
public interface EmployeRepo extends JpaRepository<Employe, Long> {

    @Query("SELECT e FROM Employe e WHERE e.profession.id = :professionId")
    List<Employe> findByProfessionId(@Param("professionId") Long professionId);
    
    @Query("SELECT e.nom, p.nom AS profession, g.date, g.heure, g.type, l.secteur " +
       "FROM Employe e " +
       "LEFT JOIN Profession p ON e.profession_id = p.idp " +
       "LEFT JOIN Garde g ON e.ide = g.employe_ide " +
       "LEFT JOIN Lieu l ON g.idg = l.garde_idg")
    List<EmployeDTO> findEmployes();
    
}
