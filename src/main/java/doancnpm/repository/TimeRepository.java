package doancnpm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Time;

public interface TimeRepository extends JpaRepository<Time, Long>{
	Optional<Time> findBysession(String session);
}
