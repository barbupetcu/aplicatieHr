package ro.facultate.aplicatieHR.entity.dic;

import ro.facultate.aplicatieHR.entity.data.Dept;
import ro.facultate.aplicatieHR.entity.data.Posturi;
import ro.facultate.aplicatieHR.entity.data.TipuriContracte;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "dic_contracte_istoric")
public class DicContracteIsto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Date;

    @Column(name = "date_eff")
    private Date dateEff;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name="tip_contract")
    private TipuriContracte tipContract;

    @Column(name = "perioada_proba")
    private Boolean perioadaProba;

    @Column(name = "perioada_proba_data")
    private Date perioadaProbaData;

    @ManyToOne
    @JoinColumn(name="dept")
    private Dept dept;

    @ManyToOne
    @JoinColumn(name="post")
    private Posturi post;

    @Column(name = "salariu")
    private Double salariu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract")
    private DicContract contract;

    @Column(name = "numar_ore")
    private Integer nrOre;

    @Column(name = "ora_inceput")
    private Time oraInceput;


    public Long getDate() {
        return Date;
    }

    public void setDate(Long date) {
        Date = date;
    }

    public java.util.Date getDateEff() {
        return dateEff;
    }

    public void setDateEff(java.util.Date dateEff) {
        this.dateEff = dateEff;
    }

    public java.util.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    public TipuriContracte getTipContract() {
        return tipContract;
    }

    public void setTipContract(TipuriContracte tipContract) {
        this.tipContract = tipContract;
    }

    public Boolean getPerioadaProba() {
        return perioadaProba;
    }

    public void setPerioadaProba(Boolean perioadaProba) {
        this.perioadaProba = perioadaProba;
    }

    public java.util.Date getPerioadaProbaData() {
        return perioadaProbaData;
    }

    public void setPerioadaProbaData(java.util.Date perioadaProbaData) {
        this.perioadaProbaData = perioadaProbaData;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Posturi getPost() {
        return post;
    }

    public void setPost(Posturi post) {
        this.post = post;
    }

    public Double getSalariu() {
        return salariu;
    }

    public void setSalariu(Double salariu) {
        this.salariu = salariu;
    }

    public DicContract getContract() {
        return contract;
    }

    public void setContract(DicContract contract) {
        this.contract = contract;
    }

    public Integer getNrOre() {
        return nrOre;
    }

    public void setNrOre(Integer nrOre) {
        this.nrOre = nrOre;
    }

    public Time getOraInceput() {
        return oraInceput;
    }

    public void setOraInceput(Time oraInceput) {
        this.oraInceput = oraInceput;
    }
}
