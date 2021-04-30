package doancnpm.security.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.converter.PostConverter;
import doancnpm.models.Grade;
import doancnpm.models.Post;
import doancnpm.models.Student;
import doancnpm.models.Subject;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.request.PostRequest;
import doancnpm.repository.GradeRepository;
import doancnpm.repository.PostRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.SubjectRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.iPostService;

@Service
public class PostService implements iPostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private PostConverter postConverter;

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private SubjectRepository subjectRepository;

//	@Override
//	public PostRequest save(PostRequest postDTO) {
//		Post postEntity = new Post();
//		if (postDTO.getId() != null) {
//			Post oldPostEntity = postRepository.findOne(postDTO.getId());
//			postEntity = postConverter.toEntity(postDTO, oldPostEntity);
//		} else {
//			postEntity = postConverter.toEntity(postDTO);
//		}
//		// User userEntity = userRepository.findOneByUsername(postDTO.getUsername());
//		// postEntity.setUser(userEntity);
//		Student studentEntity = studentRepository.findOneById(postDTO.getStudentID());
//		postEntity.setStudent(studentEntity);
//		Map<String, Boolean> schedule = postDTO.getSchedule();
//		String jsonResp = "";
//		ObjectMapper mapperObj = new ObjectMapper();
//		try {
//			jsonResp = mapperObj.writeValueAsString(schedule);
//			System.out.println(jsonResp);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		postEntity.setSchedule(jsonResp);
//		postEntity = postRepository.save(postEntity);
//		return postConverter.toDTO(postEntity);
//	}

	@Override
	public void saveCreate(String username, PostRequest postDTO) {

		User user = userRepository.findOneByusername(username);

		// Student student = new Student();
		Student oldStudent = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));

		Map<String, Boolean> schedule = postDTO.getSchedule();
		Post postEntity = new Post();
		/*
		 * Post oldPost = postRepository.findByStudentId(oldStudent.getId())
		 * .orElseThrow(() -> new UsernameNotFoundException("Post Not Found"));
		 */

		postEntity = postConverter.toEntity(postDTO);
		postEntity.setStudent(oldStudent);
		String jsonResp = "";
		ObjectMapper mapperObj = new ObjectMapper();
		try {
			jsonResp = mapperObj.writeValueAsString(schedule);
			System.out.println(jsonResp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		postEntity.setSchedule(jsonResp);
//		postEntity.setStudent(user);
//		postEntity = tutorRepository.save(tutor);
		postEntity = postRepository.save(postEntity);
		postDTO = postConverter.toDTO(postEntity);
	}

	@Override
	public void saveUpdate(String username, PostRequest postDTO, long id) {

		User user = userRepository.findOneByusername(username);

		// Student student = new Student();
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));

		Map<String, Boolean> schedule = postDTO.getSchedule();
//		Tutor tutor = new Tutor();
//		Tutor oldTutor = tutorRepository.findByuser_id(user.getId())
//				.orElseThrow(() -> new UsernameNotFoundException("Tutor Not Found"));
//		
//		tutor = tutorConverter.toTutor(addTutorRequest, oldTutor);
		Post postEntity = new Post();
		
		Post post = postRepository.findOne(id);
		
		postEntity = postConverter.toEntity(postDTO, post);
		postEntity.setStudent(student);
		String jsonResp = "";
		ObjectMapper mapperObj = new ObjectMapper();
		try {
			jsonResp = mapperObj.writeValueAsString(schedule);
			System.out.println(jsonResp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		postEntity.setSchedule(jsonResp);
//		postEntity.setStudent(user);
//		postEntity = tutorRepository.save(tutor);
		postEntity = postRepository.save(postEntity);
		postDTO = postConverter.toDTO(postEntity);
	}

	@Override
	public void delete(long id) {
		postRepository.delete(id);

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

	@Override
	public Post findPostById(Long id) {

		return postRepository.findOne(id);
	}

	@Override
	public List<Post> findAll() {
		// TODO Auto-generated method stub
		return postRepository.findAll();
	}

}