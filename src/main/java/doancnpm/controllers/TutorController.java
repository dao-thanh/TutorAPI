package doancnpm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.payload.request.AddTutorRequest;
import doancnpm.security.ITutorService;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")

public class TutorController {
	@Autowired
	private ITutorService TutorService;
	@PostMapping(value = "/tutor")
	public void createNew(@RequestBody AddTutorRequest model) {
		TutorService.save(model);
	}
}
