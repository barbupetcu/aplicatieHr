package ro.facultate.aplicatieHR.entity.app;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ro.facultate.aplicatieHR.entity.dic.DicPerso;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "App_User", //
		uniqueConstraints = { //
				@UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name") })
public class AppUser {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@Column(name = "User_Name", length = 36, nullable = false)
    private String username;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "password", length = 128, nullable = false)
    private String password;
    
    @Column(name = "Enabled", length = 1, nullable = false)
    private boolean enabled;
    
    @JsonIgnore
    @Column(name="created", updatable=false)
	@CreationTimestamp
	private LocalDateTime createDateTime;
    
    @JsonIgnore
	@Column(name="modified")
	@UpdateTimestamp
	private LocalDateTime updateDateTime;
    
    @JsonProperty("role")
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AppRole> roles;

    @JsonProperty(value="perso")
    @OneToOne(fetch = FetchType.LAZY, mappedBy="user", cascade=CascadeType.ALL)
    private DicPerso perso;

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	
    public Set<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AppRole> roles) {
        this.roles = roles;
    }

    public DicPerso getPerso() {
        return perso;
    }

    public void setPerso(DicPerso perso) {
        this.perso = perso;
    }
}