package episen.sirius.ing2.proto_back.model;




import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idE ;
    private String nom;
    private Integer age;
    @ManyToOne
    @JsonBackReference
    @ToString.Exclude
    private Profession profession;
    @OneToMany(mappedBy = "employe")
    @ToString.Exclude
    private List<Garde> gardes;
    @OneToMany(mappedBy = "employe")
    @ToString.Exclude
    private List<Absence> absences;

}
