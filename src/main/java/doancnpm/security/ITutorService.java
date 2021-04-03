package doancnpm.security;

import java.util.List;

import doancnpm.models.Tutor;
import doancnpm.payload.request.AddTutorRequest;

public interface ITutorService {
	List<Tutor> findAll();
	void save(AddTutorRequest addTutorRequest);
}
