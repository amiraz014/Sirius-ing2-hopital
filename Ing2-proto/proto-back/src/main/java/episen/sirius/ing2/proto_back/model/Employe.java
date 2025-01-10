package episen.sirius.ing2.proto_back.model;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.AnyKeyJdbcType;
import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idE ;
    private String nom;
    private String age;
    private Long idP ;


}
