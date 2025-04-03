package episen.sirius.ing2.proto_back.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Garde;

@Repository
public interface GardeRepo extends JpaRepository<Garde,Long> {

    @Modifying
    @Query("UPDATE Garde g SET g.employe.idE = :idE WHERE g.idG = :idG")
    void UpdateGarde(
            @Param("idE") Long idE,
            @Param("idG") Long idG
    );


    @Modifying
    @Query("DELETE FROM Garde g WHERE g.employe.idE = :idE")
    void DeleteByIDE(@Param("idE") Long idE);

}