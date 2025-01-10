package episen.sirius.ing2.proto_back.model;

import org.hibernate.annotations.AnyKeyJdbcType;
import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idE ;
    private String nom;
    private Integer age;
    @OneToOne
    private Profession profession;
}
