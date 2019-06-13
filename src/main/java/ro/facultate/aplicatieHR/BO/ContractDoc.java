package ro.facultate.aplicatieHR.BO;

public class ContractDoc {
    private String nrContract;
    private String sysdate;
    private String numePrenume;
    private String localitatea;
    private String strada;
    private String stradaNo;
    private String judetul;
    private String tipPerioada;
    private String startDate;
    private String endDate;
    private String functia;
    private String cnp;

    public ContractDoc(String nrContract, String sysdate, String numePrenume, String localitatea, String strada, String stradaNo, String judetul, String tipPerioada, String startDate, String endDate, String functia, String cnp) {
        this.nrContract = nrContract;
        this.sysdate = sysdate;
        this.numePrenume = numePrenume;
        this.localitatea = localitatea;
        this.strada = strada;
        this.stradaNo = stradaNo;
        this.judetul = judetul;
        this.tipPerioada = tipPerioada;
        this.startDate = startDate;
        this.endDate = endDate;
        this.functia = functia;
        this.cnp = cnp;
    }

    public String getNrContract() {
        return nrContract;
    }

    public void setNrContract(String nrContract) {
        this.nrContract = nrContract;
    }

    public String getSysdate() {
        return sysdate;
    }

    public void setSysdate(String sysdate) {
        this.sysdate = sysdate;
    }

    public String getNumePrenume() {
        return numePrenume;
    }

    public void setNumePrenume(String numePrenume) {
        this.numePrenume = numePrenume;
    }

    public String getLocalitatea() {
        return localitatea;
    }

    public void setLocalitatea(String localitatea) {
        this.localitatea = localitatea;
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

    public String getTipPerioada() {
        return tipPerioada;
    }

    public void setTipPerioada(String tipPerioada) {
        this.tipPerioada = tipPerioada;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFunctia() {
        return functia;
    }

    public void setFunctia(String functia) {
        this.functia = functia;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }
}
