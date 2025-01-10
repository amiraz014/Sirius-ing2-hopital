package episen.sirius.ing2.proto_back.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idS;
    private Integer quantite_disponible;
    private Integer  seuil;
    private Integer quantite_reapprovisionnement;
    @OneToOne
    private Medicament medicament;
    private String etat;
}
