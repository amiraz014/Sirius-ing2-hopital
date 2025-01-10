package episen.sirius.ing2.proto_back.model;


import java.sql.Date;
import java.sql.Time;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Component;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Garde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idG;
    private Date date;
    private String type;
    private Time heure;
    @OneToOne
    private Employe employe;

}
