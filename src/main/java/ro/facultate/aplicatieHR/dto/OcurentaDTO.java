package ro.facultate.aplicatieHR.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class OcurentaDTO {

    @JsonProperty(value = "marca")
    private Long marca;

    @JsonProperty(value = "dateEff")
    private Date dateEff;

    public Long getMarca() {
        return marca;
    }

    public void setMarca(Long marca) {
        this.marca = marca;
    }

    public Date getDateEff() {
        return dateEff;
    }

    public void setDateEff(Date dateEff) {
        this.dateEff = dateEff;
    }
}
