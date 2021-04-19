package doancnpm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import doancnpm.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
}
