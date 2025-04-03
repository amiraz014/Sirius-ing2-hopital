package episen.sirius.ing2.proto_back.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
<<<<<<< HEAD
import lombok.ToString;
=======
>>>>>>> prod
import org.springframework.stereotype.Component;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idP;
    private String nom;
    @OneToMany(mappedBy = "profession")
    @JsonBackReference
<<<<<<< HEAD
    @ToString.Exclude
=======
>>>>>>> prod
    private List<Employe> employes;
}
