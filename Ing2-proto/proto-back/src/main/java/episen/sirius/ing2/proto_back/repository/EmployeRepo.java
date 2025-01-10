

package episen.sirius.ing2.proto_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import episen.sirius.ing2.proto_back.model.Employe;
@Repository
public interface EmployeRepo extends JpaRepository<Employe, Long> {


    

    
}