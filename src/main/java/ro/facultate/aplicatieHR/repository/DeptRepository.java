package ro.facultate.aplicatieHR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.facultate.aplicatieHR.entity.Dept;


public interface DeptRepository extends JpaRepository<Dept, Long> {}