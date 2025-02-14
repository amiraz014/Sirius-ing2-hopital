package episen.sirius.ing2.proto_back.model;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idH;
    private Integer quantite;
    private LocalDate date_mouvement;
    private String type;
    @OneToOne
    private Medicament medicament;


}
