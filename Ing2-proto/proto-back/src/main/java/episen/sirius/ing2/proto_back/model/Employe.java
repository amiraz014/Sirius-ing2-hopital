package episen.sirius.ing2.proto_back.model;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idE ;
    private String nom;

    private Integer age;
    @ManyToOne
    @JoinColumn(name = "profession_id", nullable = false)
    private Profession profession;

}
