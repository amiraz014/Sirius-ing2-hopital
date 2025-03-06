package episen.sirius.ing2.proto_back.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class Lieu {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long idL;
    private String secteur;
    @OneToMany
    private List<Garde> gardes;
    
}
