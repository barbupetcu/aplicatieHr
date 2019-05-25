package ro.facultate.aplicatieHR.entity.dic;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "dic_contracte")
public class DicContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @OneToMany(
            mappedBy = "contract",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DicContracteIsto> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persoana")
    private DicPerso persoana;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<DicContracteIsto> getComments() {
        return comments;
    }

    public void setComments(List<DicContracteIsto> comments) {
        this.comments = comments;
    }

    public DicPerso getPersoana() {
        return persoana;
    }

    public void setPersoana(DicPerso persoana) {
        this.persoana = persoana;
    }
}
