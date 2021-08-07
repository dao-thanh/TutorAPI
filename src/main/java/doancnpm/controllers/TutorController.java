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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.models.Grade;
import doancnpm.models.Subject;
import doancnpm.models.Tutor;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.response.TutorOutput;
import doancnpm.repository.GradeRepository;
import doancnpm.repository.SubjectRepository;
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

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	private GradeRepository gradeRepository;

	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(Object.class, IgnoreHibernatePropertiesInJackson.class);
		return mapper;
	}

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private abstract class IgnoreHibernatePropertiesInJackson {
	}

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
		Set<Grade> setGrades = tutorData.getGrades();
		Set<String> grades = new HashSet<String>();
		for (Grade grade : setGrades) {
			grades.add(grade.getGradename());
		}
		tutorOutput.setGrades(grades);

		Set<Subject> setSubjects = tutorData.getSubjects();
		Set<String> subjects = new HashSet<String>();
		for (Subject subject : setSubjects) {
			subjects.add(subject.getSubjectname());
		}
		tutorOutput.setSubjects(subjects);

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
			Set<Grade> setGrades = tutors.get(i).getGrades();
			Set<String> grades = new HashSet<String>();
			for (Grade grade : setGrades) {
				grades.add(grade.getGradename());
			}
			tutorOutput.setGrades(grades);

			Set<Subject> setSubjects = tutors.get(i).getSubjects();
			Set<String> subjects = new HashSet<String>();
			for (Subject subject : setSubjects) {
				subjects.add(subject.getSubjectname());
			}
			tutorOutput.setSubjects(subjects);

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

		return response;

	}

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

	@GetMapping(value = "/tutor/search")
	public Map<String, List<TutorOutput>> searchTutor(@RequestParam(required = false) String grade, String address,
			String subject) {
		List<Tutor> tutors = new ArrayList<Tutor>();
		List<Subject> subjects = new ArrayList<Subject>();
		Subject mon = subjectRepository.findBysubjectname(subject);
		subjects.add(mon);
		List<Grade> grades = new ArrayList<Grade>();
		Grade lop = gradeRepository.findBygradename(grade);
		grades.add(lop);
		if (grade == null && address == null && subject == null)
			tutors = tutorRepository.findAll();
		else if (subject != null && grade == null && address == null) {
			tutors = tutorRepository.findBySubjects(subjects);
		} else if (grade != null && address == null && subject == null) {
			tutors = tutorRepository.findByGrades(grades);
		} else if (subject == null && grade == null && address != null) {
			tutors = tutorRepository.findByAddress(address);
		} else if (subject == null && grade != null && address != null) {
			tutors = tutorRepository.findByGradesInAndAddressIn(grades, address);
		} else if (subject != null && grade != null && address == null) {
			tutors = tutorRepository.findBySubjectsInAndGradesIn(subjects, grades);
		} else if (subject != null && grade == null && address != null) {
			tutors = tutorRepository.findBySubjectsInAndAddressIn(subjects, address);
		} else {
			tutors = tutorRepository.findBySubjectsInAndGradesInAndAddressIn(subjects, grades, address);
		}
		List<TutorOutput> tutorOutputs = new ArrayList<TutorOutput>();
		for (int i = 0; i < tutors.size(); i++) {
			String schedules = tutors.get(i).getSchedule();
			TutorOutput tutorOutput = new TutorOutput();
			tutorOutput.setId(tutors.get(i).getId());
			tutorOutput.setName(tutors.get(i).getUser().getName());
			tutorOutput.setPhonenumber(tutors.get(i).getUser().getPhonenumber());
			;
			tutorOutput.setQualification(tutors.get(i).getQualification());
			;
			tutorOutput.setAvatar(tutors.get(i).getAvatar());
			tutorOutput.setRating(tutors.get(i).getRating());
			tutorOutput.setDescription(tutors.get(i).getDescription());
			tutorOutput.setAddress(tutors.get(i).getAddress());
			Set<Subject> setSubjects = tutors.get(i).getSubjects();
			Set<String> subjects1 = new HashSet<String>();
			for (Subject subject1 : setSubjects) {
				subjects1.add(subject1.getSubjectname());
			}
			tutorOutput.setSubjects(subjects1);

			Set<Grade> setGrades = tutors.get(i).getGrades();
			Set<String> grades1 = new HashSet<String>();
			for (Grade grade1 : setGrades) {
				grades1.add(grade1.getGradename());
			}
			tutorOutput.setGrades(grades1);
			try {
				Map<String, Boolean> schedule = new ObjectMapper().readValue(schedules, HashMap.class);
				System.out.println(schedule);
				tutorOutput.setSchedules(schedule);
			} catch (IOException e) {

				e.printStackTrace();
			}
			tutorOutputs.add(tutorOutput);
		}
		Map<String, List<TutorOutput>> response = new HashMap<String, List<TutorOutput>>();
		response.put("tutor", tutorOutputs);
		return response;

	}
}