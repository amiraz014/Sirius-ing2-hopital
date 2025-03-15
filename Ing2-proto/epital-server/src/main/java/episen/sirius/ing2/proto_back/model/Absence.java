package episen.sirius.ing2.proto_back.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idA;
    private String motif;
    private LocalDate date_absence;
    private LocalTime time_absence;
    @ManyToOne
    private Employe employe;



}
