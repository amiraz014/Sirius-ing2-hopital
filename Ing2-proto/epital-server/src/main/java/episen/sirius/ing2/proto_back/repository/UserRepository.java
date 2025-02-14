package episen.sirius.ing2.proto_back.repository;

import episen.sirius.ing2.proto_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
