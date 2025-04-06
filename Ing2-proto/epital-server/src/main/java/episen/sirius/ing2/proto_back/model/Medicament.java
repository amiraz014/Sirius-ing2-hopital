package episen.sirius.ing2.proto_back.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idm;

    private String description;
    private String nom;
    private String niveau_utilisation;
    private String categorie;
}