package ro.facultate.aplicatieHR.entity.dic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "DIC_PERSO")
public class DicPerso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "marca")
    private Long marca;
	
	@Column(name = "nume", nullable = false)
	private String name;
	
	@Column(name = "prenume", nullable = false)
	private String lastName;

	@Column(name = "sex", nullable = false)
	private String sex;

	@Column(name = "data_nasterii", nullable = false)
	private Date dataNasterii;

	@Column(name = "cnp", nullable = false)
	private Long cnp;

	@Column(name = "judetul_nasterii", nullable = false)
	private Integer judetulNasterii;

	@Column(name = "orasul_nasterii", nullable = false)
	private Integer orasulNasterii;
	
	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "pseudonim")
	private String pseudonim;

	@JsonProperty("address")
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="address")
	private DicAdresa address;

	@JsonIgnore
    @Column(name="created", updatable=false)
	@CreationTimestamp
	private LocalDateTime createDateTime;
    
    @JsonIgnore
	@Column(name="modified")
	@UpdateTimestamp
	private LocalDateTime updateDateTime;

	@Column(name = "contract_activ")
	private Boolean contractActiv;

	public Long getMarca() {
		return marca;
	}

	public void setMarca(Long id) {
		this.marca = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getDataNasterii() {
		return dataNasterii;
	}

	public void setDataNasterii(Date dataNasterii) {
		this.dataNasterii = dataNasterii;
	}

	public Long getCnp() {
		return cnp;
	}

	public void setCnp(Long cnp) {
		this.cnp = cnp;
	}

	public Integer getJudetulNasterii() {
		return judetulNasterii;
	}

	public void setJudetulNasterii(Integer judetulNasterii) {
		this.judetulNasterii = judetulNasterii;
	}

	public Integer getOrasulNasterii() {
		return orasulNasterii;
	}

	public void setOrasulNasterii(Integer orasulNasterii) {
		this.orasulNasterii = orasulNasterii;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DicAdresa getAddress() {
		return address;
	}

	public void setAddress(DicAdresa address) {
		this.address = address;
	}

	public String getPseudonim() {
		return pseudonim;
	}

	public void setPseudonim(String pseudonim) {
		this.pseudonim = pseudonim;
	}

	public Boolean getContractActiv() {
		return contractActiv;
	}

	public void setContractActiv(Boolean contractActiv) {
		this.contractActiv = contractActiv;
	}
}
