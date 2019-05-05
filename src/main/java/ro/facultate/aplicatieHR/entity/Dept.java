package ro.facultate.aplicatieHR.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "DEPT")
public class Dept {
	@Id
	@Column(name = "id", nullable = false, updatable=false)
	private Long deptId;
	
	@Column(name = "nume_dept", length=30)
	private String numeDept;

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getNumeDept() {
		return numeDept;
	}

	public void setNumeDept(String numeDept) {
		this.numeDept = numeDept;
	}
	
}