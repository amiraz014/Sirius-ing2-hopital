package episen.sirius.ing2.proto_back.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
public class EmployeDTO {
    private String nom;
    private String profession;
    private LocalDate date;
    private LocalTime heure;
    private String type;
    private String secteur;

}
