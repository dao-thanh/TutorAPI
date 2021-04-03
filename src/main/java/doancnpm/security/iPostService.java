package doancnpm.security;

import java.util.List;

import org.springframework.data.domain.Pageable;

import doancnpm.payload.request.PostRequest;

public interface iPostService {
	PostRequest save(PostRequest postDTO);

	void delete(long[] ids);

	List<PostRequest> findAll(Pageable pageable);

	int totalItem();

}
