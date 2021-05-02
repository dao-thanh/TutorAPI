package doancnpm.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.models.Tutor;

import doancnpm.payload.request.AddTutorRequest;
import doancnpm.security.ITutorService;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.request.AddUserRequest;
import doancnpm.payload.response.TutorOutput;
import doancnpm.payload.response.TutorResponse;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ITutorService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")

public class TutorController {
	@Autowired
	private ITutorService tutorService;
	
	public ObjectMapper getObjectMapper() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.addMixIn(Object.class, IgnoreHibernatePropertiesInJackson.class);
	    return mapper;
	}

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private abstract class IgnoreHibernatePropertiesInJackson{ }
	
//	@GetMapping(value = "/tutor")
//	public Map<String,List<Tutor>> all(){
//		System.out.println("ok");
//		List<Tutor> tutors = tutorService.findAll();
////		System.out.println(tutors.get(0).getSubject().getSubjectname()+"Thanh");
//		Map<String,List<Tutor>> response=new HashMap<String, List<Tutor>>();
//		response.put("tutors", tutors);
//	}
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TutorRepository tutorRepository;

	@GetMapping("/tutor/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR') or hasRole('STUDENT')")
	public ResponseEntity<TutorOutput> getTutorById(@PathVariable("id") long id) {
		Tutor tutorData = tutorService.findTutorById(id);
		
		String schedule = tutorData.getSchedule();
		TutorOutput tutorOutput = new TutorOutput();
		tutorOutput.setId(tutorData.getId());
		tutorOutput.setQualification(tutorData.getQualification());
		tutorOutput.setAvatar(tutorData.getAvatar());
		tutorOutput.setRating(tutorData.getRating());
		tutorOutput.setDescription(tutorData.getDescription());
		tutorOutput.setAddress(tutorData.getAddress());
		tutorOutput.setName(tutorData.getUser().getName());
		tutorOutput.setPhonenumber(tutorData.getUser().getPhonenumber());
		tutorOutput.setSubjects(tutorData.getSubjects());
		tutorOutput.setGrades(tutorData.getGrades());

		if (schedule != null) {
			// tutorOutput.setSchedule(jsonObject);
			try {
				Map<String, Boolean> schedules = new ObjectMapper().readValue(schedule, HashMap.class);
				System.out.println(schedules);
				tutorOutput.setSchedules(schedules);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (tutorData != null) {
			return new ResponseEntity<>(tutorOutput, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value = "/tutor")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR') or hasRole('STUDENT')")
	public Map<String, List<TutorOutput>> showTutor() {

		System.out.println("ok");
		List<Tutor> tutors = tutorService.findAll();
		List<TutorOutput> tutorOutputs = new ArrayList<TutorOutput>();
		for (int i = 0; i < tutors.size(); i++) {

			String schedule = tutors.get(i).getSchedule();
			// JSONObject jsonObject= new JSONObject(schedule );

			TutorOutput tutorOutput = new TutorOutput();
			tutorOutput.setId(tutors.get(i).getId());
			tutorOutput.setQualification(tutors.get(i).getQualification());
			tutorOutput.setAvatar(tutors.get(i).getAvatar());
			tutorOutput.setRating(tutors.get(i).getRating());
			tutorOutput.setDescription(tutors.get(i).getDescription());
			tutorOutput.setAddress(tutors.get(i).getAddress());
			tutorOutput.setName(tutors.get(i).getUser().getName());
			tutorOutput.setPhonenumber(tutors.get(i).getUser().getPhonenumber());
			tutorOutput.setSubjects(tutors.get(i).getSubjects());
			tutorOutput.setGrades(tutors.get(i).getGrades());

			if (schedule != null) {
				// tutorOutput.setSchedule(jsonObject);
				try {
					Map<String, Boolean> schedules = new ObjectMapper().readValue(schedule, HashMap.class);
					System.out.println(schedules);
					tutorOutput.setSchedules(schedules);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			tutorOutputs.add(tutorOutput);

		}

		Map<String, List<TutorOutput>> response = new HashMap<String, List<TutorOutput>>();

		response.put("tutors", tutorOutputs);
//		//return ResponseEntity.ok(response);
//		Gson gson=new Gson();
//		
//		String data=gson.toJson(tutors);
//		System.out.println("Hehe"+data);
//		return null;

		return response;

	}

//	@GetMapping(value = "/pagetutor")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR') or hasRole('STUDENT')")
//	public TutorResponse showTutorPage(@RequestParam("page") int page, @RequestParam("limit") int limit){
//		
//		
//		TutorResponse result = new TutorResponse();
//		result.setPage(page);
//		Pageable pageable = new PageRequest(page - 1, limit);
//		result.setListResult(tutorService.findAllPage(pageable));
//		
//		result.setTotalPage((int) Math.ceil((double) (tutorService.totalItem()) / limit));
//		
//		return result;
//	
//	}

//	@PostMapping(value = "/tutor")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR')")
//	public void createNew(@RequestBody AddTutorRequest model) {
//	
//		tutorService.save(model);
//		
//	}

	@PutMapping(value = "/tutor")
	@PreAuthorize("hasRole('TUTOR')")
	public String updateTutor(HttpServletRequest request, @RequestBody AddTutorRequest model) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}	
		tutorService.save(username, model);
		String message = "Update tutor is success !\n";
		return message;
	}

	@DeleteMapping(value = "/tutor")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(@RequestBody long[] ids) {
		tutorService.delete(ids);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
}