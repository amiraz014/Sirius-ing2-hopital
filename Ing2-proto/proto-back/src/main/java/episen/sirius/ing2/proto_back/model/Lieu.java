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
@NoArgsConstructor
@Entity
public class Lieu {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long idL;
    private String secteur;
    @OneToOne
    private Garde garde;
}
