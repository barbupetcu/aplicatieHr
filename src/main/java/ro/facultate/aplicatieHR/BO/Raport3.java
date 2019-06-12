package ro.facultate.aplicatieHR.BO;

import java.util.Date;

public class Raport3 {
    private Long MARCA;
    private String NUME;
    private String PRENUME;
    private String DEPARTAMENT;
    private String POST;
    private String TIP_CONTRACT;
    private Date DATA_INCEPUT_CONTRACT;
    private Date DATA_SFARSIT_CONTRACT;
    private String DURATA_CONTRACT;

    public Raport3(Long MARCA, String NUME, String PRENUME, String DEPARTAMENT, String POST, String TIP_CONTRACT, Date DATA_INCEPUT_CONTRACT, Date DATA_SFARSIT_CONTRACT, String DURATA_CONTRACT) {
        this.MARCA = MARCA;
        this.NUME = NUME;
        this.PRENUME = PRENUME;
        this.DEPARTAMENT = DEPARTAMENT;
        this.POST = POST;
        this.TIP_CONTRACT = TIP_CONTRACT;
        this.DATA_INCEPUT_CONTRACT = DATA_INCEPUT_CONTRACT;
        this.DATA_SFARSIT_CONTRACT = DATA_SFARSIT_CONTRACT;
        this.DURATA_CONTRACT = DURATA_CONTRACT;
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

    public Date getDATA_SFARSIT_CONTRACT() {
        return DATA_SFARSIT_CONTRACT;
    }

    public void setDATA_SFARSIT_CONTRACT(Date DATA_SFARSIT_CONTRACT) {
        this.DATA_SFARSIT_CONTRACT = DATA_SFARSIT_CONTRACT;
    }

    public String getDURATA_CONTRACT() {
        return DURATA_CONTRACT;
    }

    public void setDURATA_CONTRACT(String DURATA_CONTRACT) {
        this.DURATA_CONTRACT = DURATA_CONTRACT;
    }
}
