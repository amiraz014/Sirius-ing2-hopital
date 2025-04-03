package episen.sirius.ing2.proto_back.model;


import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@NoArgsConstructor
public class Garde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idG;
    private LocalDate date;
    private String type;
    private LocalTime heure;
    @ManyToOne
    @JsonBackReference
    @ToString.Exclude
    private Employe employe;
    @ManyToOne
    @ToString.Exclude
    private Lieu lieu;

}