

package episen.sirius.ing2.proto_back.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import episen.sirius.ing2.proto_back.model.Employe;
@Repository
public interface EmployeRepo extends JpaRepository<Employe, Long> {

    @Query("SELECT e FROM Employe e WHERE e.profession_id IN :professionIDs")
    List<Employe> findAllByProfessionIds(@Param("professionIDs") List<Long> professionIDs);
}
