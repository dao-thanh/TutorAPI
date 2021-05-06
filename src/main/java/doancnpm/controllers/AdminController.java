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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.models.Post;
import doancnpm.models.Subject;
import doancnpm.payload.response.PostOut;
import doancnpm.repository.PostRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.SubjectRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.iPostService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin
@RestController
public class AdminController {

	@Autowired
	PostRepository postRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	private iPostService postService;

	@Autowired
	private JwtUtils jwtUtils;
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
	@GetMapping(value = "/post/admin")
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
	
	@GetMapping("/post/admin/{id}")
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
	
	@DeleteMapping(value = "/api/post/admin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deletePost(HttpServletRequest request, @PathVariable("id") long id) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		postService.admin_delete(username, id);
		System.out.println("Delete is successed");
	}
}
