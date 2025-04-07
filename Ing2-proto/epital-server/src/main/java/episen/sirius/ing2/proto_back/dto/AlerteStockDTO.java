package episen.sirius.ing2.proto_back.dto;

import java.util.Objects;

public class AlerteStockDTO {
    private Long medicamentId;
    private String nomMedicament;
    private int stockActuel;
    private int seuilCritique;
    private int quantiteACommander;
    private String etat;

    public AlerteStockDTO() {
    }

    public AlerteStockDTO(Long medicamentId, String nomMedicament, int stockActuel,
                          int seuilCritique, int quantiteACommander, String etat) {
        this.medicamentId = medicamentId;
        this.nomMedicament = nomMedicament;
        this.stockActuel = stockActuel;
        this.seuilCritique = seuilCritique;
        this.quantiteACommander = quantiteACommander;
        this.etat = etat;
    }

    // Getters and Setters
    public Long getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(Long medicamentId) {
        this.medicamentId = medicamentId;
    }

    public String getNomMedicament() {
        return nomMedicament;
    }

    public void setNomMedicament(String nomMedicament) {
        this.nomMedicament = nomMedicament;
    }

    public int getStockActuel() {
        return stockActuel;
    }

    public void setStockActuel(int stockActuel) {
        this.stockActuel = stockActuel;
    }

    public int getSeuilCritique() {
        return seuilCritique;
    }

    public void setSeuilCritique(int seuilCritique) {
        this.seuilCritique = seuilCritique;
    }

    public int getQuantiteACommander() {
        return quantiteACommander;
    }

    public void setQuantiteACommander(int quantiteACommander) {
        this.quantiteACommander = quantiteACommander;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlerteStockDTO that = (AlerteStockDTO) o;
        return stockActuel == that.stockActuel &&
                seuilCritique == that.seuilCritique &&
                quantiteACommander == that.quantiteACommander &&
                Objects.equals(medicamentId, that.medicamentId) &&
                Objects.equals(nomMedicament, that.nomMedicament) &&
                Objects.equals(etat, that.etat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicamentId, nomMedicament, stockActuel, seuilCritique, quantiteACommander, etat);
    }

    @Override
    public String toString() {
        return "AlerteStockDTO{" +
                "medicamentId=" + medicamentId +
                ", nomMedicament='" + nomMedicament + '\'' +
                ", stockActuel=" + stockActuel +
                ", seuilCritique=" + seuilCritique +
                ", quantiteACommander=" + quantiteACommander +
                ", etat='" + etat + '\'' +
            '}';
}
}