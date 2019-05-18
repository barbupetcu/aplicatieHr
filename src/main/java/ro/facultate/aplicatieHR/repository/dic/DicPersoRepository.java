package ro.facultate.aplicatieHR.repository.dic;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.facultate.aplicatieHR.entity.dic.DicPerso;


public interface DicPersoRepository extends JpaRepository<DicPerso, Long> {
	
	public DicPerso findByid(Long id);
	
}