package episen.sirius.ing2.proto_back.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ids;

    private Integer quantite_disponible;
    private Integer seuil;
    private Integer quantite_reapprovisionnement;
    private String etat;

    @ManyToOne
    @JoinColumn(name = "medicament_idm")
    private Medicament medicament;
}