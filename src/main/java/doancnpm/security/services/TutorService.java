package doancnpm.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doancnpm.converter.TutorConverter;
import doancnpm.models.Tutor;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.repository.TutorRepository;
import doancnpm.security.ITutorService;

@Service
public class TutorService implements ITutorService{
	
	@Autowired
	private TutorRepository tutorRepository;
	 
	@Autowired TutorConverter tutorConverter;
	@Override
	public void save(AddTutorRequest addTutorRequest) {	
		Tutor tutor = tutorConverter.toTutor(addTutorRequest);
		tutor = tutorRepository.save(tutor);	
	}	
}
