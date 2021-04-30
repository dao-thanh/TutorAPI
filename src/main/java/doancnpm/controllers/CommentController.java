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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.models.Comment;
import doancnpm.models.User;
import doancnpm.payload.request.CommentRequest;
import doancnpm.payload.request.InvitationRequest;
import doancnpm.security.ICommentService;
import doancnpm.security.jwt.JwtUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	ICommentService commentService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping(value = "/comment")
	@PreAuthorize("hasRole('STUDENT')")
	public void createComment(HttpServletRequest request, @RequestBody CommentRequest model) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		commentService.save(username, model);
	}
	
	
//	@GetMapping(value = "/message")
//	public Map<String, List<Comment>> all(){
//		List<Comment> messages = messageService.all();
//		Map<String,List<Comment>> response = new HashMap<String, List<Comment>>();
//		response.put("messages", messages);
//		return response;
//	}
//	
//	@GetMapping("/message/{id}")
//	 public ResponseEntity<Comment> getMessageById(@PathVariable("id") long id) {
//	    //User userData = userService.getUserById(id);
//	    Comment message = messageService.getMessageById(id);
//
//	    if (message != null) {
//	      return new ResponseEntity<>(message, HttpStatus.OK);
//	    } else {
//	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }
//	  }
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
}
