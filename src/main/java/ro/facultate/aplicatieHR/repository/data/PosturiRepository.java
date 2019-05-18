package ro.facultate.aplicatieHR.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.facultate.aplicatieHR.entity.data.Posturi;

import java.util.List;

public interface PosturiRepository extends JpaRepository<Posturi, Integer> {
    List<Posturi> findAllByDeptId(Integer deptId);
}
