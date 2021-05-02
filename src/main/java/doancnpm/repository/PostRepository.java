package doancnpm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Post;
import doancnpm.models.Tutor;

public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findById(Long id);
	
	Page<Post> findBySubject(String subject, Pageable paging);

	Page<Post> findByGrade(String grade, Pageable paging);
	
	Page<Post> findByAddress(String address, Pageable paging);
	
	Page<Post> findByGradeInAndSubjectIn(String grade, String subject, Pageable paging);
	
	Page<Post> findByGradeInAndAddressIn(String grade, String address, Pageable paging);
	
	Page<Post> findBySubjectInAndAddressIn(String subject, String address, Pageable paging);

	Page<Post> findByGradeInAndSubjectInAndAddress(String grade, String subject, String address, Pageable paging);

	Post findOneById(Long id);
	
	List<Post> findByStudent_id(long student_id);
	
	Optional<Post> findByStudentId(long Id);

}
