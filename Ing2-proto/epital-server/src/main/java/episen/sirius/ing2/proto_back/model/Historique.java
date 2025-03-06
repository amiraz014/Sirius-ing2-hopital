// package episen.sirius.ing2.proto_back.model;

// import jakarta.persistence.*;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import java.time.LocalDate;


// @Entity
// @Data
// @NoArgsConstructor
// public class Historique {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long idH;

//     private Integer quantite;
//     private LocalDate date_mouvement;
//     private String type;

//     @ManyToOne
//     @JoinColumn(name = "medicament_id")
//     private Medicament medicament;
// }
