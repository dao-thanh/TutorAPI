package doancnpm.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.models.User;
import doancnpm.payload.request.AddUserRequest;
import doancnpm.security.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping(value = "/user")
	//@PreAuthorize("hasRole('TUTOR')")
	public Map<String,List<User>> all(){
		List<User> user = userService.all();
		Map<String,List<User>> response=new HashMap<String, List<User>>();
		response.put("users", user);
		return response;
	}
	
	@GetMapping("/user/{id}")
	
	  public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
	    User userData = userService.getUserById(id);

	    if (userData != null) {
	      return new ResponseEntity<>(userData, HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
	@PutMapping(value = "/user/{id}")
	public String updateUser(@RequestBody AddUserRequest model, @PathVariable("id") long id) {
		
//	    Optional<User> userEdit = userService.findUserById(userId);  
//	    userEdit.ifPresent(user -> model.addAttribute("user", user));  
		model.setId(id);
		userService.save(model);
	    return "Update user is success";  
	  }  
	
	@DeleteMapping(value = "/user")
	public void deleteUser(@RequestBody long[] ids) {
		userService.delete(ids);
	}
	
}
