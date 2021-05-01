package doancnpm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import doancnpm.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	Optional<Student> findByuser_id(long id);
}
