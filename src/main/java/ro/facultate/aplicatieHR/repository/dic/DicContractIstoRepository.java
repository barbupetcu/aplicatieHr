package ro.facultate.aplicatieHR.repository.dic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.facultate.aplicatieHR.entity.dic.DicContract;
import ro.facultate.aplicatieHR.entity.dic.DicContracteIsto;
import ro.facultate.aplicatieHR.projection.Ocurente;

import java.util.Date;
import java.util.List;


public interface DicContractIstoRepository extends JpaRepository<DicContracteIsto, Long> {

    List<DicContracteIsto> findByContract_Persoana_MarcaOrderByDateEffDesc(Long marca);

    List<Ocurente> findByContract_Persoana_Marca(Long id);

    DicContracteIsto findByDateEffAndContract_Persoana_Marca(Date dateEff, Long marca);

    @Query("select a from DicContracteIsto a " +
            "where a.contract.persoana.contractActiv = true and a.dateEff = (select max (b.dateEff) from DicContracteIsto b where a.contract.persoana.marca=b.contract.persoana.marca) " +
            "group by a.contract.persoana.marca")
    List<DicContracteIsto> findAngajatiLastOccurence();

}