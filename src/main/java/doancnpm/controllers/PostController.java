package doancnpm.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.controllers.output.PostOutput;
import doancnpm.models.Post;
import doancnpm.models.Student;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.request.PostRequest;
import doancnpm.payload.response.PostOut;
import doancnpm.payload.response.TutorOutput;
import doancnpm.repository.PostRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.iPostService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin
@RestController
public class PostController {
	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	private iPostService postService;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping(value = "/api/post")
	@PreAuthorize("hasRole('STUDENT')")
	public String createPost(HttpServletRequest request, @RequestBody PostRequest model) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		postService.saveCreate(username, model);
		String message = "Create Post is success !\n";
		return message;
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
	
	@GetMapping(value = "/api/post")
	@PreAuthorize("hasRole('STUDENT')")
	public Map<String, List<PostOut>> getPostByIdStudent(HttpServletRequest request) {
		
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));
		List<Post> post = postService.findByIdStudent(student.getId());
		
		List<PostOut> postOuts = new ArrayList<PostOut>();
		for (int i = 0; i < post.size(); i++) {
			String schedules = post.get(i).getSchedule();
			PostOut postOut = new PostOut();
			postOut.setId(post.get(i).getId());
			postOut.setAddress(post.get(i).getAddress());
			postOut.setGrade(post.get(i).getGrade());
			postOut.setSubject(post.get(i).getSubject());
			postOut.setDescription(post.get(i).getDescription());
			postOut.setPhoneNumber(post.get(i).getPhoneNumber());
			postOut.setPrice(post.get(i).getPrice());
			postOut.setTitle(post.get(i).getTitle());
			postOut.setIdStudent(student.getId());
			try {
				Map<String, Boolean> schedule = new ObjectMapper().readValue(schedules, HashMap.class);
				System.out.println(schedule);
				postOut.setSchedule(schedule);
			} catch (IOException e) {

				e.printStackTrace();
			}
			postOuts.add(postOut);
		}
		Map<String, List<PostOut>> response = new HashMap<String, List<PostOut>>();
		response.put("post", postOuts);
		return response;
	}
	
	@PutMapping(value = "/api/post/{id}")
	@PreAuthorize("hasRole('STUDENT')")
	public String updatePost(HttpServletRequest request, @RequestBody PostRequest model, @PathVariable("id") long id) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		postService.saveUpdate(username, model, id);
		String message = "Update Post is success !\n";
		return message;
	}

	@DeleteMapping(value = "/api/post/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
	public void deletePost(HttpServletRequest request, @PathVariable("id") long id) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		postService.delete(username, id);
		System.out.println("Delete is successed");
	}

	@GetMapping("/post/{id}")
	public Map<String, PostOut> getPostById(@PathVariable("id") long id) {
		Post post = postService.findPostById(id);

		String schedules = post.getSchedule();
		PostOut postOut = new PostOut();
		postOut.setId(post.getId());
		postOut.setAddress(post.getAddress());
		postOut.setGrade(post.getGrade());
		postOut.setSubject(post.getSubject());
		postOut.setDescription(post.getDescription());
		postOut.setPhoneNumber(post.getPhoneNumber());
		postOut.setPrice(post.getPrice());
		postOut.setTitle(post.getTitle());
		postOut.setIdStudent(post.getStudent().getId());
		try {
			Map<String, Boolean> schedule = new ObjectMapper().readValue(schedules, HashMap.class);
			System.out.println(schedule);
			postOut.setSchedule(schedule);
		} catch (IOException e) {

			e.printStackTrace();
		}
		Map<String,PostOut> response = new HashMap<String, PostOut>();
		response.put("post", postOut);
		return response;
	}

	@GetMapping(value = "/post")
	public Map<String, List<PostOut>> showPost() {
		List<Post> post = postService.findAll();
		List<PostOut> postOuts = new ArrayList<PostOut>();
		for (int i = 0; i < post.size(); i++) {
			String schedules = post.get(i).getSchedule();
			PostOut postOut = new PostOut();
			postOut.setId(post.get(i).getId());
			postOut.setAddress(post.get(i).getAddress());
			postOut.setGrade(post.get(i).getGrade());
			postOut.setSubject(post.get(i).getSubject());
			postOut.setDescription(post.get(i).getDescription());
			postOut.setPhoneNumber(post.get(i).getPhoneNumber());
			postOut.setPrice(post.get(i).getPrice());
			postOut.setTitle(post.get(i).getTitle());
			postOut.setIdStudent(post.get(i).getStudent().getId());
			try {
				Map<String, Boolean> schedule = new ObjectMapper().readValue(schedules, HashMap.class);
				System.out.println(schedule);
				postOut.setSchedule(schedule);
			} catch (IOException e) {

				e.printStackTrace();
			}
			postOuts.add(postOut);
		}
		Map<String, List<PostOut>> response = new HashMap<String, List<PostOut>>();
		response.put("post", postOuts);
		return response;
	}

	@GetMapping("/post/apisearch")
	public ResponseEntity<Map<String, Object>> getAllTutors(@RequestParam(required = false) String subject,
			String grade, String address, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {
		try {
			List<Post> posts = new ArrayList<Post>();
			Pageable paging = new PageRequest(page, size);

			Page<Post> pageTuts;
			if (subject == null && grade == null && address == null)
				pageTuts = postRepository.findAll(paging);

			else if (subject != null && grade == null && address == null)
				pageTuts = postRepository.findBySubject(subject, paging);

			else if (subject == null && grade != null && address == null)
				pageTuts = postRepository.findByGrade(grade, paging);

			else if (subject == null && grade == null && address != null)
				pageTuts = postRepository.findByAddress(address, paging);

			else if (subject == null && grade != null && address != null)
				pageTuts = postRepository.findByGradeInAndAddressIn(grade, address, paging);

			else if (subject != null && grade == null && address != null)
				pageTuts = postRepository.findBySubjectInAndAddressIn(subject, address, paging);

			else if (subject != null && grade != null && address == null)
				pageTuts = postRepository.findByGradeInAndSubjectIn(grade, subject, paging);
			else {
				pageTuts = postRepository.findByGradeInAndSubjectInAndAddress(grade, subject, address, paging);
			}
			posts = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("posts", posts);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}