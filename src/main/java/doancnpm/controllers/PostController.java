package doancnpm.controllers;

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

import doancnpm.controllers.output.PostOutput;
import doancnpm.models.Post;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.request.PostRequest;
import doancnpm.repository.PostRepository;
import doancnpm.security.iPostService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin
@RestController
public class PostController {
	@Autowired
	PostRepository postRepository;

	@Autowired
	private iPostService postService;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping(value = "/post")
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

//	@PutMapping(value = "/tutor")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR')")
//	public String updateUser(HttpServletRequest request, @RequestBody AddTutorRequest model) {
//		
//		String jwt = parseJwt(request);
//    	String username ="";
//		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//			username = jwtUtils.getUserNameFromJwtToken(jwt);
//		}	
//		tutorService.save(username, model);
//		String message = "Update tutor is success !\n";
//	    return message;  
//	}
//	@PutMapping(value = "/post/{id}")
//	public PostRequest updatePost(@RequestBody PostRequest model, @PathVariable("id") long id) {
//		model.setId(id);
//		return postService.save(model);
//	}
	@PutMapping(value = "/post/{id}")
	@PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
	public String updatePost(HttpServletRequest request, @RequestBody PostRequest model, @PathVariable("id") long id) {
		//model.setId(id);
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		postService.saveUpdate(username, model, id);
		String message = "Update Post is success !\n";
		return message;
	}

	@DeleteMapping(value = "/post")
	@PreAuthorize("hasRole('STUDENT')")
	public void deletePost(@RequestBody long[] ids) {
		postService.delete(ids);
	}

	@GetMapping(value = "/post")
	@PreAuthorize("hasRole('STUDENT')")
	public PostOutput showPost(@RequestParam("page") int page, @RequestParam("limit") int limit) {
		PostOutput result = new PostOutput();
		result.setPage(page);
		Pageable pageable = new PageRequest(page - 1, limit);
		result.setListResultDTO(postService.findAll(pageable));
		result.setTotalPage((int) Math.ceil((double) (postService.totalItem()) / limit));
		return result;
	}

	@GetMapping("/post/{id}")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<Post> readPostById(@PathVariable("id") long id) {
		Post postData = postService.findPostById(id);

		if (postData != null) {
			return new ResponseEntity<>(postData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/post/apisearch")
	@PreAuthorize("hasRole('STUDENT')")
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