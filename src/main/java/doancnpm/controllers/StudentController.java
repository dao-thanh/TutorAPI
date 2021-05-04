package doancnpm.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.models.Student;
import doancnpm.payload.request.StudentRequest;
import doancnpm.security.IStudentService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class StudentController {
	@Autowired
	private IStudentService studentService;

	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping(value = "/student")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, List<Student>> all() {
		List<Student> student = studentService.all();
		Map<String, List<Student>> response = new HashMap<String, List<Student>>();
		response.put("students", student);
		return response;
	}

	@GetMapping("/student/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
	public ResponseEntity<Student> getUserById(@PathVariable("id") long id) {
		Student student = studentService.getStudentById(id);

		if (student != null) {
			return new ResponseEntity<>(student, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/student")
	@PreAuthorize("hasRole('STUDENT')")
	public String updateUser(HttpServletRequest request, @RequestBody StudentRequest model) {

//	    Optional<User> userEdit = userService.findUserById(userId);  
//	    userEdit.ifPresent(user -> model.addAttribute("user", user));
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		studentService.save(username, model);

		return "Update is success";
	}

	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
	
}
