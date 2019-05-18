package ro.facultate.aplicatieHR.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.facultate.aplicatieHR.entity.app.AppUser;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	
	public AppUser findByUsername(String username);
}