package ro.facultate.aplicatieHR.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.facultate.aplicatieHR.entity.app.AppRole;


public interface AppRoleRepository extends JpaRepository<AppRole, Long>{
	
	public AppRole findByroleName(String roleName);
}
