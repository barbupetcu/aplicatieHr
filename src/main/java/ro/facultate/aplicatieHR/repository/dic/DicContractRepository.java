package ro.facultate.aplicatieHR.repository.dic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.facultate.aplicatieHR.entity.dic.DicContract;

import java.util.Date;


public interface DicContractRepository extends JpaRepository<DicContract, Long> {

    int countAllByStartDateGreaterThan(Date date);

    int countAllByPreavizTrueAndPreavizDateGreaterThanEqual(Date endDate);

}