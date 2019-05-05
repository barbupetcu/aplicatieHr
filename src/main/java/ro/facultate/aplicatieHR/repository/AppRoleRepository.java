package ro.facultate.aplicatieHR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.facultate.aplicatieHR.entity.AppRole;


public interface AppRoleRepository extends JpaRepository<AppRole, Long>{
	
	public AppRole findByroleName(String roleName);
}
