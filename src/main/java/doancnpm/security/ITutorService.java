package doancnpm.security;

import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import doancnpm.models.Tutor;
import doancnpm.payload.request.AddTutorRequest;

public interface ITutorService {
	List<Tutor> findAll();
	void save(AddTutorRequest addTutorRequest);
	Tutor findTutorById(Long id);
	void delete(long[] ids);
}
