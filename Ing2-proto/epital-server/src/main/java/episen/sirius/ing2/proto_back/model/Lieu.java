package episen.sirius.ing2.proto_back.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@Entity
public class Lieu {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long idL;
    private String secteur;
    @OneToMany(mappedBy = "lieu")
    @JsonBackReference
    @ToString.Exclude
    private List<Garde> gardes;

}