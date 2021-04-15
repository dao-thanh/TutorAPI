package doancnpm.security.services;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.converter.TutorConverter;
import doancnpm.models.Grade;

import doancnpm.models.Subject;

import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.repository.GradeRepository;

import doancnpm.repository.SubjectRepository;

import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ITutorService;

@Service
public class TutorService implements ITutorService{
	
	@Autowired
	private TutorRepository tutorRepository;
	
	@Autowired
	private GradeRepository gradeRepository;
	@Autowired
	private SubjectRepository subjectRepository;
		
	 
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	private TutorConverter tutorConverter;
	
	@Override
	public List<Tutor> findAll() {
		return tutorRepository.findAll();
	}
	
	@Override
	public Tutor findTutorById(Long id) {
		
		return tutorRepository.findOne(id);
	}
	@Override
	public void save(AddTutorRequest addTutorRequest) {	
		
		
		User user = userRepository.findOneByusername(addTutorRequest.getUsername());
		
		Set<String> strSubject = addTutorRequest.getSubjects();
		Set<Subject> subjects = new HashSet<>();
		strSubject.forEach(subject ->{
			switch (subject) {
			case "Toán":
				Subject toan = subjectRepository.findBysubjectname("Toán")
					.orElseThrow(() -> new RuntimeException("Error: Subject is not found."));
				
				subjects.add(toan);
				break;
			case "Tiếng anh":
				Subject tienganh = subjectRepository.findBysubjectname("Tiếng anh")
					.orElseThrow(() -> new RuntimeException("Error: Subject is not found."));
				subjects.add(tienganh);
				break;
			}
		});
		
		Set<String> strGrade = addTutorRequest.getGrade();
		Set<Grade> grades = new HashSet<>();
		
		strGrade.forEach(grade ->{
			switch(grade) {
			case "Lớp 1":
				Grade lop1 = gradeRepository.findBygradename("Lớp 1")
				.orElseThrow(() -> new RuntimeException("Error: Grade is not found."));
				grades.add(lop1);
				break;
			case "Lớp 2":
				Grade lop2 = gradeRepository.findBygradename("Lớp 2")
				.orElseThrow(() -> new RuntimeException("Error: Grade is not found."));
				grades.add(lop2);
				break;
			}
		});
		
		Map<String,Boolean> schedule = addTutorRequest.getSchedule();
//		 try {
//	            String jsonResp = mapperObj.writeValueAsString(inputMap);
//	            System.out.println(jsonResp);
//	        } catch (IOException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }
		
		Tutor tutor = new Tutor();
		
		if(addTutorRequest.getId() != null) {
			Tutor oldTutor = tutorRepository.findOne(addTutorRequest.getId());
			tutor = tutorConverter.toTutor(addTutorRequest, oldTutor);
		}else {
			tutor = tutorConverter.toTutor(addTutorRequest);
		}
		//Tutor tutor = tutorConverter.toTutor(addTutorRequest);	
		tutor.setGrades(grades);
		tutor.setSubjects(subjects);
		
		String jsonResp = "";
		ObjectMapper mapperObj = new ObjectMapper();
		try {
			jsonResp = mapperObj.writeValueAsString(schedule);
            System.out.println(jsonResp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
        }
		
		tutor.setSchedule(jsonResp);
			
		tutor.setUser(user);	
		tutor = tutorRepository.save(tutor);	
	}

	@Override
	public void delete(long[] ids) {
		for(long item:ids) {
			tutorRepository.delete(item);
		}
		
	}	
}
