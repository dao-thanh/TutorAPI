package doancnpm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import doancnpm.payload.request.MessageRequest;
import doancnpm.security.IMessageService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MessageController {
	@Autowired
	IMessageService messageService;
	
	
//	@GetMapping(value = "/message")
//
//	public Map<String,List<User>> all(){
//		List<User> user = userService.all();
//		Map<String,List<User>> response=new HashMap<String, List<User>>();
//		response.put("users", user);
//		return response;
//	}
	@PostMapping(value = "/message")
	public void createNew(@RequestBody MessageRequest model) {
		messageService.save(model);
	}
}
