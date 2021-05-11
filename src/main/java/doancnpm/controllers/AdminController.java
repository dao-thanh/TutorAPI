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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.models.ERole;
import doancnpm.models.Post;
import doancnpm.models.Role;
import doancnpm.models.Subject;
import doancnpm.models.User;
import doancnpm.payload.response.PostOut;
import doancnpm.payload.response.UserOutput;
import doancnpm.repository.PostRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.SubjectRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.IUserService;
import doancnpm.security.iPostService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin
@RestController
public class AdminController {
	@Autowired
	private IUserService userService;

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

	@GetMapping(value = "/admin/user")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, List<UserOutput>> showUser() {
		List<User> user = userService.all();
		List<UserOutput> userOutputs = new ArrayList<UserOutput>();
		for (int i = 0; i < user.size(); i++) {
			UserOutput userOutput = new UserOutput();
			userOutput.setId(user.get(i).getId());
			userOutput.setUsername(user.get(i).getUsername());
			userOutput.setPassword(user.get(i).getPassword());
			userOutput.setEmail(user.get(i).getEmail());
			userOutput.setPhonenumber(user.get(i).getPhonenumber());
			Set<Role> setRoles = user.get(i).getRoles();
			Set<ERole> roles = new HashSet<ERole>();
			for (Role role : setRoles) {
				roles.add(role.getName());
			}
			userOutput.setRoles(roles);
			userOutputs.add(userOutput);
		}
		Map<String, List<UserOutput>> response = new HashMap<String, List<UserOutput>>();
		response.put("user", userOutputs);
		return response;

	}

	@GetMapping("/admin/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, UserOutput> getUserById(@PathVariable("id") long id) {
		User user = userRepository.findOne(id);
		UserOutput userOutput = new UserOutput();
		userOutput.setId(user.getId());
		userOutput.setUsername(user.getUsername());
		userOutput.setPassword(user.getPassword());
		userOutput.setEmail(user.getEmail());
		userOutput.setPhonenumber(user.getPhonenumber());
		Set<Role> setRoles = user.getRoles();
		Set<ERole> roles = new HashSet<ERole>();
		for (Role role : setRoles) {
			roles.add(role.getName());
		}
		userOutput.setRoles(roles);

		Map<String, UserOutput> response = new HashMap<String, UserOutput>();
		response.put("user",userOutput);
		return response;
}

	@DeleteMapping(value = "/admin/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(HttpServletRequest request, @PathVariable("id") long id) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		userService.admin_delete(username, id);
		System.out.println("Delete is successed");
	}
}
