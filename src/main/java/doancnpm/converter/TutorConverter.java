package doancnpm.converter;

import org.springframework.stereotype.Component;

import doancnpm.models.Tutor;
import doancnpm.payload.request.AddTutorRequest;

@Component
public class TutorConverter {
	public Tutor toTutor(AddTutorRequest addTutorRequest)
	{
		Tutor tutor = new Tutor();
		tutor.setAvatar(addTutorRequest.getAvatar());
		tutor.setAddressId(addTutorRequest.getAddressId());
		tutor.setDescription(addTutorRequest.getDescription());
		tutor.setGradeId(addTutorRequest.getGradeId());
		tutor.setSubjectId(addTutorRequest.getSubjectId());
		tutor.setTime(addTutorRequest.getTime());
		tutor.setQualification(addTutorRequest.getQualification());
		return tutor;
	}
}
