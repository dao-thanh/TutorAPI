package doancnpm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Grade;


public interface GradeRepository extends JpaRepository<Grade, Long>{
	Optional<Grade> findBygradename(String gradeName);
}
