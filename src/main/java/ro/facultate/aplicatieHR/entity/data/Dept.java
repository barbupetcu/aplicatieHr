package ro.facultate.aplicatieHR.entity.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEPT")
public class Dept {
	@Id
	@Column(name = "id", nullable = false, updatable=false)
	private Integer deptId;
	
	@Column(name = "nume_dept", length=30)
	private String numeDept;

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getNumeDept() {
		return numeDept;
	}

	public void setNumeDept(String numeDept) {
		this.numeDept = numeDept;
	}
	
}