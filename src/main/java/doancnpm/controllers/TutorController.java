package doancnpm.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.models.Tutor;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.request.AddUserRequest;
import doancnpm.security.ITutorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")

public class TutorController {
	@Autowired
	private ITutorService tutorService;
	
//	public ObjectMapper getObjectMapper() {
//	    ObjectMapper mapper = new ObjectMapper();
//	    mapper.addMixIn(Object.class, IgnoreHibernatePropertiesInJackson.class);
//	    return mapper;
//	}
//
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	private abstract class IgnoreHibernatePropertiesInJackson{ }
	
	@GetMapping("/tutor/{id}")
	  public ResponseEntity<Tutor> getTutorById(@PathVariable("id") long id) {
	    Tutor tutorData = tutorService.findTutorById(id);

	    if (tutorData != null) {
	      return new ResponseEntity<>(tutorData, HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
	@GetMapping(value = "/tutor")
	public Map<String,List<Tutor>> all(){
		System.out.println("ok");
		List<Tutor> tutors = tutorService.findAll();
//		System.out.println(tutors.get(0).getSubject().getSubjectname()+"Thanh");
		Map<String,List<Tutor>> response=new HashMap<String, List<Tutor>>();
		response.put("tutors", tutors);
//		//return ResponseEntity.ok(response);
//		Gson gson=new Gson();
//		
//		String data=gson.toJson(tutors);
//		System.out.println("Hehe"+data);
//		return null;
		//Map<String,String> a=new HashMap<String, String>();
	    //a.put("name","Thanh");
		
		return response;
	
	}
	
	
	@PostMapping(value = "/tutor")
	public void createNew(@RequestBody AddTutorRequest model) {
		tutorService.save(model);
	}
	
	@PutMapping(value = "/tutor/{id}")
	public String updateUser(@RequestBody AddTutorRequest model, @PathVariable("id") long id) {  
		model.setId(id);
		tutorService.save(model);
	    return "Update tutor is success";  
	  }  
	
	@DeleteMapping(value = "/tutor")
	public void deleteUser(@RequestBody long[] ids) {
		tutorService.delete(ids);
	}
	
}