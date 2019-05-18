package ro.facultate.aplicatieHR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.facultate.aplicatieHR.entity.DicPerso;


public interface DicPersoRepository extends JpaRepository<DicPerso, Long> {
	
	public DicPerso findByid(Long id);
	
}