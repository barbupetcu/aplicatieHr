package ro.facultate.aplicatieHR.repository.dic;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.facultate.aplicatieHR.entity.dic.DicPerso;
import ro.facultate.aplicatieHR.projection.AngajatHeader;

import java.util.List;


public interface DicPersoRepository extends JpaRepository<DicPerso, Long> {
	
	DicPerso findByMarca(Long id);

	List<AngajatHeader> findAllBy();

	List<AngajatHeader> findAllByContractActiv(Boolean contractActiv);

	long countAllByContractActiv(boolean contractActiv);
	
}