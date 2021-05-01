package doancnpm.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import doancnpm.converter.PostConverter;
import doancnpm.models.Post;
import doancnpm.models.User;
import doancnpm.payload.request.PostRequest;
import doancnpm.repository.PostRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.iPostService;

@Service
public class PostService implements iPostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostConverter postConverter;

	@Override
	public PostRequest save(PostRequest postDTO) {
		Post postEntity = new Post();
		if (postDTO.getId() != null) {
			Post oldPostEntity = postRepository.findOne(postDTO.getId());
			postEntity = postConverter.toEntity(postDTO, oldPostEntity);
		} else {
			postEntity = postConverter.toEntity(postDTO);
		}
		User userEntity = userRepository.findOneByusername(postDTO.getUsername());
		postEntity.setUser(userEntity);
		postEntity = postRepository.save(postEntity);
		return postConverter.toDTO(postEntity);
	}

	@Override
	public void delete(long[] ids) {
		for (long item : ids) {
			postRepository.delete(item);
		}

	}

	@Override
	public List<PostRequest> findAll(Pageable pageable) {
		List<PostRequest> results = new ArrayList<>();
		List<Post> entities = postRepository.findAll(pageable).getContent();
		for (Post item : entities) {
			PostRequest postDTO = postConverter.toDTO(item);
			results.add(postDTO);
		}
		return results;
	}

	@Override
	public int totalItem() {

		return (int) postRepository.count();
	}

}