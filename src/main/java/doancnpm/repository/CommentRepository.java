package doancnpm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
}
