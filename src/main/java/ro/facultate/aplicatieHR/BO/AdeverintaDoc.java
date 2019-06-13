package ro.facultate.aplicatieHR.BO;

public class AdeverintaDoc {

    private String numePrenume;
    private String cnp;
    private String localiate;
    private String strada;
    private String stradaNo;
    private String judetul;
    private String functia;
    private String startDate;
    private String motiv;
    private String salariu;

    public String getNumePrenume() {
        return numePrenume;
    }

    public void setNumePrenume(String numePrenume) {
        this.numePrenume = numePrenume;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getLocaliate() {
        return localiate;
    }

    public void setLocaliate(String localiate) {
        this.localiate = localiate;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getStradaNo() {
        return stradaNo;
    }

    public void setStradaNo(String stradaNo) {
        this.stradaNo = stradaNo;
    }

    public String getJudetul() {
        return judetul;
    }

    public void setJudetul(String judetul) {
        this.judetul = judetul;
    }

    public String getFunctia() {
        return functia;
    }

    public void setFunctia(String functia) {
        this.functia = functia;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getMotiv() {
        return motiv;
    }

    public void setMotiv(String motiv) {
        this.motiv = motiv;
    }

    public String getSalariu() {
        return salariu;
    }

    public void setSalariu(String salariu) {
        this.salariu = salariu;
    }

    public AdeverintaDoc(String numePrenume, String cnp, String localiate, String strada, String stradaNo, String judetul, String functia, String startDate, String motiv, String salariu) {
        this.numePrenume = numePrenume;
        this.cnp = cnp;
        this.localiate = localiate;
        this.strada = strada;
        this.stradaNo = stradaNo;
        this.judetul = judetul;
        this.functia = functia;
        this.startDate = startDate;
        this.motiv = motiv;
        this.salariu = salariu;
    }
}
