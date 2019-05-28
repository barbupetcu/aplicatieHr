package ro.facultate.aplicatieHR.entity.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TIPURI_CONTRACTE")
public class TipuriContracte {

    @Id
    @Column(name = "code", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TipuriContracte(String id) {
        this.id = Integer.parseInt(id);
    }

    public TipuriContracte() {}
}
