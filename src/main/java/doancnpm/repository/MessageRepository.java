package doancnpm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
}
