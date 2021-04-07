package doancnpm.security.services;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doancnpm.converter.TutorConverter;
import doancnpm.models.Grade;
import doancnpm.models.Schedule;
import doancnpm.models.Subject;
import doancnpm.models.Time;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.repository.GradeRepository;
import doancnpm.repository.ScheduleRepository;
import doancnpm.repository.SubjectRepository;
import doancnpm.repository.TimeRepository;
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
	private ScheduleRepository scheduleRepository;
	@Autowired
	private TimeRepository timeRepository;
	 
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
		
		Set<String> strSchedule = addTutorRequest.getTeachingDate();
		Set<Schedule> schedules = new HashSet<>();
		Set<String> strTime = addTutorRequest.getSession();
		Set<Time> times = new HashSet<>();
		
		strSchedule.forEach(schedule -> {
			switch(schedule) {
			case "Thứ 2":
				Schedule thu2 = scheduleRepository.findByteachingDate("Thứ 2")
					.orElseThrow(() -> new RuntimeException("Error: Grade is not found."));
				
				strTime.forEach(time -> {
					switch(time) {
					case "Sáng":
						Time sang = timeRepository.findBysession("Sáng")
							.orElseThrow(() -> new RuntimeException("Error: Session is not found."));
						times.add(sang);
						break;
					case "Chiều":
						Time chieu = timeRepository.findBysession("Chiều")
							.orElseThrow(() -> new RuntimeException("Error: Session is not found."));
						times.add(chieu);
						break;
					case "Tối":
						Time toi = timeRepository.findBysession("Tối")
							.orElseThrow(() -> new RuntimeException("Error: Session is not found."));
						times.add(toi);
						break;
					}
				});
				thu2.setTimes(times);
				schedules.add(thu2);
				break;
			case "Thứ 3":
				Schedule thu3 = scheduleRepository.findByteachingDate("Thứ 2")
					.orElseThrow(() -> new RuntimeException("Error: Grade is not found."));
				strTime.forEach(time -> {
					switch(time) {
					case "Sáng":
						Time sang = timeRepository.findBysession("Sáng")
							.orElseThrow(() -> new RuntimeException("Error: Session is not found."));
						times.add(sang);
						break;
					case "Chiều":
						Time chieu = timeRepository.findBysession("Chiều")
							.orElseThrow(() -> new RuntimeException("Error: Session is not found."));
						times.add(chieu);
						break;
					case "Tối":
						Time toi = timeRepository.findBysession("Tối")
							.orElseThrow(() -> new RuntimeException("Error: Session is not found."));
						times.add(toi);
						break;
					}
				});
				thu3.setTimes(times);
				schedules.add(thu3);
				break;
			}
		});
				
		Tutor tutor = tutorConverter.toTutor(addTutorRequest);	
		tutor.setGrades(grades);
		tutor.setSubjects(subjects);
		
		tutor.setSchedules(schedules);
		
		tutor.setUser(user);
		
		
		tutor = tutorRepository.save(tutor);	
	}	
}
