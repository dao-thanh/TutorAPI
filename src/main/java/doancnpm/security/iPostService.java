package doancnpm.security;

import java.util.List;

import org.springframework.data.domain.Pageable;

import doancnpm.models.Post;
import doancnpm.models.Tutor;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.request.PostRequest;

public interface iPostService {
	//PostRequest save(PostRequest postDTO);
	void saveCreate(String username, PostRequest postDTO);
	void saveUpdate(String username, PostRequest postDTO, long id);

	void delete(long id);

	List<PostRequest> findAll(Pageable pageable);

	int totalItem();

	Post findPostById(Long id);
	
	List<Post> findAll();

}
