package ro.facultate.aplicatieHR.repository.dic;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.facultate.aplicatieHR.entity.dic.DicContract;
import ro.facultate.aplicatieHR.entity.dic.DicContracteIsto;
import ro.facultate.aplicatieHR.projection.Ocurente;

import java.util.Date;
import java.util.List;


public interface DicContractIstoRepository extends JpaRepository<DicContracteIsto, Long> {

    List<DicContracteIsto> findByContract_Persoana_MarcaOrderByDateEffDesc(Long marca);

    List<Ocurente> findByContract_Persoana_Marca(Long id);

    DicContracteIsto findByDateEffAndContract_Persoana_Marca(Date dateEff, Long marca);



}