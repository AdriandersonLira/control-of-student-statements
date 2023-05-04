package br.com.mercury.mercuryweb.repositories;

import br.com.mercury.mercuryweb.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
