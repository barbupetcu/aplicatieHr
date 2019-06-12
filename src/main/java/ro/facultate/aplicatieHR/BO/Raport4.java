package ro.facultate.aplicatieHR.BO;

import java.util.Date;

public class Raport4 {
    private Long MARCA;
    private String NUME;
    private String PRENUME;
    private String DEPARTAMENT;
    private String POST;
    private Date DATA_INCEPUT_CONTRACT;
    private String USERNAME;
    private Date DATA_CREARE_CONT;

    public Raport4(Long MARCA, String NUME, String PRENUME, String DEPARTAMENT, String POST, Date DATA_INCEPUT_CONTRACT, String USERNAME, Date DATA_CREARE_CONT) {
        this.MARCA = MARCA;
        this.NUME = NUME;
        this.PRENUME = PRENUME;
        this.DEPARTAMENT = DEPARTAMENT;
        this.POST = POST;
        this.DATA_INCEPUT_CONTRACT = DATA_INCEPUT_CONTRACT;
        this.USERNAME = USERNAME;
        this.DATA_CREARE_CONT = DATA_CREARE_CONT;
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

    public Date getDATA_INCEPUT_CONTRACT() {
        return DATA_INCEPUT_CONTRACT;
    }

    public void setDATA_INCEPUT_CONTRACT(Date DATA_INCEPUT_CONTRACT) {
        this.DATA_INCEPUT_CONTRACT = DATA_INCEPUT_CONTRACT;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public Date getDATA_CREARE_CONT() {
        return DATA_CREARE_CONT;
    }

    public void setDATA_CREARE_CONT(Date DATA_CREARE_CONT) {
        this.DATA_CREARE_CONT = DATA_CREARE_CONT;
    }
}
