package ro.facultate.aplicatieHR.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.facultate.aplicatieHR.entity.data.Dept;


public interface DeptRepository extends JpaRepository<Dept, Integer> {
}