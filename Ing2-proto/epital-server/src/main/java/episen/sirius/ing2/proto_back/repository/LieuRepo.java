package episen.sirius.ing2.proto_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import episen.sirius.ing2.proto_back.model.Lieu;

import java.util.List;

@Repository
public interface LieuRepo  extends JpaRepository<Lieu,Long>{
} 
