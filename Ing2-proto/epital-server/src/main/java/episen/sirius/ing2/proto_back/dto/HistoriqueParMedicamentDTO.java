package episen.sirius.ing2.proto_back.dto;

import java.time.LocalDate;
import episen.sirius.ing2.proto_back.dto.HistoriqueParMedicamentDTO;

public class HistoriqueParMedicamentDTO {
    private Long idMedicament;
    private String nomMedicament;
    private Integer nombreSorties;
    private Integer quantiteTotaleSortie;
    private LocalDate derniereSortie;

    // Constructeur
    public HistoriqueParMedicamentDTO(Long idMedicament, String nomMedicament, Integer nombreSorties, Integer quantiteTotaleSortie, LocalDate derniereSortie) {
        this.idMedicament = idMedicament;
        this.nomMedicament = nomMedicament;
        this.nombreSorties = nombreSorties;
        this.quantiteTotaleSortie = quantiteTotaleSortie;
        this.derniereSortie = derniereSortie;
    }

    // Getters et Setters
    public Long getIdMedicament() {
        return idMedicament;
    }

    public void setIdMedicament(Long idMedicament) {
        this.idMedicament = idMedicament;
    }

    public String getNomMedicament() {
        return nomMedicament;
    }

    public void setNomMedicament(String nomMedicament) {
        this.nomMedicament = nomMedicament;
    }

    public Integer getNombreSorties() {
        return nombreSorties;
    }

    public void setNombreSorties(Integer nombreSorties) {
        this.nombreSorties = nombreSorties;
    }

    public Integer getQuantiteTotaleSortie() {
        return quantiteTotaleSortie;
    }

    public void setQuantiteTotaleSortie(Integer quantiteTotaleSortie) {
        this.quantiteTotaleSortie = quantiteTotaleSortie;
    }

    public LocalDate getDerniereSortie() {
        return derniereSortie;
    }

    public void setDerniereSortie(LocalDate derniereSortie) {
        this.derniereSortie = derniereSortie;
    }
}
