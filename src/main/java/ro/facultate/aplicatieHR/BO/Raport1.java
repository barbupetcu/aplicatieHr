package ro.facultate.aplicatieHR.BO;

import java.util.Date;

public class Raport1 {
    private Long MARCA;
    private String NUME;
    private String PRENUME;
    private String DEPARTAMENT;
    private String POST;
    private Double SALARIU;
    private String TIP_CONTRACT;
    private Date DATA_INCEPUT_CONTRACT;
    private Date DATA_ULTIMA_MODIFICARE;

    public Raport1(Long MARCA, String NUME, String PRENUME, String DEPARTAMENT, String POST, Double SALARIU, String TIP_CONTRACT, Date DATA_INCEPUT_CONTRACT, Date DATA_ULTIMA_MODIFICARE) {
        this.MARCA = MARCA;
        this.NUME = NUME;
        this.PRENUME = PRENUME;
        this.DEPARTAMENT = DEPARTAMENT;
        this.POST = POST;
        this.SALARIU = SALARIU;
        this.TIP_CONTRACT = TIP_CONTRACT;
        this.DATA_INCEPUT_CONTRACT = DATA_INCEPUT_CONTRACT;
        this.DATA_ULTIMA_MODIFICARE = DATA_ULTIMA_MODIFICARE;
    }

    public Long getMARCA() {
        return MARCA;
    }

    public void setMARCA(Long MARCA) {
        this.MARCA = MARCA;
    }

    public String getNUME() {
        return NUME;
    }

    public void setNUME(String NUME) {
        this.NUME = NUME;
    }

    public String getPRENUME() {
        return PRENUME;
    }

    public void setPRENUME(String PRENUME) {
        this.PRENUME = PRENUME;
    }

    public String getDEPARTAMENT() {
        return DEPARTAMENT;
    }

    public void setDEPARTAMENT(String DEPARTAMENT) {
        this.DEPARTAMENT = DEPARTAMENT;
    }

    public String getPOST() {
        return POST;
    }

    public void setPOST(String POST) {
        this.POST = POST;
    }

    public Double getSALARIU() {
        return SALARIU;
    }

    public void setSALARIU(Double SALARIU) {
        this.SALARIU = SALARIU;
    }

    public String getTIP_CONTRACT() {
        return TIP_CONTRACT;
    }

    public void setTIP_CONTRACT(String TIP_CONTRACT) {
        this.TIP_CONTRACT = TIP_CONTRACT;
    }

    public Date getDATA_INCEPUT_CONTRACT() {
        return DATA_INCEPUT_CONTRACT;
    }

    public void setDATA_INCEPUT_CONTRACT(Date DATA_INCEPUT_CONTRACT) {
        this.DATA_INCEPUT_CONTRACT = DATA_INCEPUT_CONTRACT;
    }

    public Date getDATA_ULTIMA_MODIFICARE() {
        return DATA_ULTIMA_MODIFICARE;
    }

    public void setDATA_ULTIMA_MODIFICARE(Date DATA_ULTIMA_MODIFICARE) {
        this.DATA_ULTIMA_MODIFICARE = DATA_ULTIMA_MODIFICARE;
    }
}
