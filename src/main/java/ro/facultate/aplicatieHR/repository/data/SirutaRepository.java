package ro.facultate.aplicatieHR.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.facultate.aplicatieHR.entity.data.Siruta;
import ro.facultate.aplicatieHR.projection.Judete;
import ro.facultate.aplicatieHR.projection.Orase;

import java.util.List;

public interface SirutaRepository extends JpaRepository<Siruta, Integer> {

    List<Judete> findDistinctByOrderByCountyName();
    List<Orase> findByCountyIdOrderByCityName(Integer countyId);

    Siruta findFirstByCountyIdAndId(Integer countyId, Long id);

    Siruta findFirstByCountyId(Integer countyId);
}
