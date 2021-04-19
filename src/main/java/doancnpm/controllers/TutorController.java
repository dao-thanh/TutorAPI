package doancnpm.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.request.AddUserRequest;
import doancnpm.security.ITutorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")

public class TutorController {
	@Autowired
	private ITutorService tutorService;
	
	@GetMapping("/tutor/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR') or hasRole('STUDENT')")
	  public ResponseEntity<Tutor> getTutorById(@PathVariable("id") long id) {
	    Tutor tutorData = tutorService.findTutorById(id);

	    if (tutorData != null) {
	      return new ResponseEntity<>(tutorData, HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
	@GetMapping(value = "/tutor")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR') or hasRole('STUDENT')")
	public Map<String,List<Tutor>> all(){
		System.out.println("ok");
		List<Tutor> tutors = tutorService.findAll();
//		for(int i=0;i<tutors.size();i++)
//		{
//			String schedule = tutors.get(i).getSchedule();
//			JSONObject jsonObject= new JSONObject(schedule );
//			System.out.println(jsonObject);
//		}
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
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR')")
	public String createNew(@RequestBody AddTutorRequest model, @RequestParam("file") MultipartFile file) {
		String fileName = tutorService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
		tutorService.save(model, file);
		return fileDownloadUri;
	}
	
	@PutMapping(value = "/tutor/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR')")
	public String updateUser(@RequestBody AddTutorRequest model, @PathVariable("id") long id, @RequestParam("file") MultipartFile file) {  
		model.setId(id);
		String fileName = tutorService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
		tutorService.save(model, file);
		String message = "Update tutor is success !\n" + fileDownloadUri;
	    return message;  
	}  
	
	@DeleteMapping(value = "/tutor")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(@RequestBody long[] ids) {
		tutorService.delete(ids);
	}
	
}