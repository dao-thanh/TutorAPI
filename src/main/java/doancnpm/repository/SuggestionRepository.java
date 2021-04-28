package doancnpm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Suggestion;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

}
