package doancnpm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Tutor;


public interface TutorRepository extends JpaRepository<Tutor, Long>{
	Optional<Tutor> findByuser_id(long id);
}
