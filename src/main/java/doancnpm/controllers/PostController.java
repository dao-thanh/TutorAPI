package doancnpm.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

import doancnpm.models.Grade;
import doancnpm.models.Post;
import doancnpm.models.Student;
import doancnpm.models.Subject;
import doancnpm.models.User;
import doancnpm.payload.request.PostRequest;
import doancnpm.payload.response.PostOut;
import doancnpm.repository.GradeRepository;
import doancnpm.repository.PostRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.SubjectRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.iPostService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin
@RestController
public class PostController {
	@Autowired
	PostRepository postRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	private GradeRepository gradeRepository;
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
			postOut.setIdStudent(post.get(i).getStudent().getId());
			postOut.setAddress(post.get(i).getAddress());
			postOut.setGrade(post.get(i).getGrade().getGradename());
			Set<Subject> setSubjects = post.get(i).getSubjects();
			Set<String> subjects = new HashSet<String>();
			for (Subject subject : setSubjects) {
				subjects.add(subject.getSubjectname());
			}
			postOut.setSubjects(subjects);
			postOut.setDescription(post.get(i).getDescription());
			postOut.setPhoneNumber(post.get(i).getPhoneNumber());
			postOut.setPrice(post.get(i).getPrice());
			postOut.setTitle(post.get(i).getTitle());

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
		postOut.setIdStudent(post.getStudent().getId());
		postOut.setAddress(post.getAddress());
		postOut.setGrade(post.getGrade().getGradename());
		Set<Subject> setSubjects = post.getSubjects();
		Set<String> subjects = new HashSet<String>();
		for (Subject subject : setSubjects) {
			subjects.add(subject.getSubjectname());
		}
		postOut.setSubjects(subjects);
		postOut.setDescription(post.getDescription());
		postOut.setPhoneNumber(post.getPhoneNumber());
		postOut.setPrice(post.getPrice());
		postOut.setTitle(post.getTitle());

		try {
			Map<String, Boolean> schedule = new ObjectMapper().readValue(schedules, HashMap.class);
			System.out.println(schedule);
			postOut.setSchedule(schedule);
		} catch (IOException e) {

			e.printStackTrace();
		}
		Map<String, PostOut> response = new HashMap<String, PostOut>();
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
			postOut.setGrade(post.get(i).getGrade().getGradename());
			Set<Subject> setSubjects = post.get(i).getSubjects();
			Set<String> subjects = new HashSet<String>();
			for (Subject subject : setSubjects) {
				subjects.add(subject.getSubjectname());
			}
			postOut.setSubjects(subjects);
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

	@GetMapping("/post/search")
	public Map<String, List<PostOut>> searchPost(@RequestParam(required = false) String grade, String address,
			String subject) {
		List<Post> posts = new ArrayList<Post>();
		// Pageable paging = new PageRequest(page, size);
		Grade lop = gradeRepository.findBygradename(grade);
		// .orElseThrow(() -> new RuntimeException("Error: Grade is not found."));
		int grade_id = 0;
		if (lop != null) {
			grade_id = lop.getId();
		}
		List<Subject> subjects = new ArrayList<Subject>();

		Subject mon = subjectRepository.findBysubjectname(subject);
//				.orElseThrow(() -> new RuntimeException("Error: Subject is not found."));

		subjects.add(mon);

		// List<Post> pageTuts = null;
		// List<Post> post = new ArrayList<Post>();

//		System.out.println("ok");
//		 post =  postRepository.findBySubjects(subjects);
//		 for(int i=0;i<post.size();i++)
//			 System.out.println(post.get(i).getTitle());

		if (grade == null && address == null && subject == null)
			posts = postRepository.findAll();

		else if (subject != null && grade == null && address == null) {
			posts = postRepository.findBySubjects(subjects);

		} else if (grade != null && address == null && subject == null)
			posts = postRepository.findByGrade_id(grade_id);

		else if (subject == null && grade == null && address != null)
			posts = postRepository.findByAddress(address);

		else if (subject == null && grade != null && address != null)
			posts = postRepository.findByGrade_idInAndAddressIn(grade_id, address);

		else if (subject != null && grade != null && address == null)
			posts = postRepository.findBySubjectsInAndGrade_idIn(subjects, grade_id);

		else if (subject != null && grade == null && address != null)
			posts = postRepository.findBySubjectsInAndAddressIn(subjects, address);

		else
			posts = postRepository.findBySubjectsInAndGrade_idInAndAddressIn(subjects, grade_id, address);

		// posts = pageTuts.getContent();
		List<PostOut> postOuts = new ArrayList<PostOut>();
		for (int i = 0; i < posts.size(); i++) {
			String schedules = posts.get(i).getSchedule();
			PostOut postOut = new PostOut();
			postOut.setId(posts.get(i).getId());
			postOut.setAddress(posts.get(i).getAddress());
			postOut.setGrade(posts.get(i).getGrade().getGradename());
			Set<Subject> setSubjects = posts.get(i).getSubjects();
			Set<String> subjects1 = new HashSet<String>();
			for (Subject subject1 : setSubjects) {
				subjects1.add(subject1.getSubjectname());
			}
			postOut.setSubjects(subjects1);
			postOut.setDescription(posts.get(i).getDescription());
			postOut.setPhoneNumber(posts.get(i).getPhoneNumber());
			postOut.setPrice(posts.get(i).getPrice());
			postOut.setTitle(posts.get(i).getTitle());
			postOut.setIdStudent(posts.get(i).getStudent().getId());
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

}