package doancnpm.security;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import doancnpm.models.Tutor;
import doancnpm.payload.request.AddTutorRequest;

public interface ITutorService {
	List<Tutor> findAll(String fileName);
	String storeFile(MultipartFile file);
	void save(AddTutorRequest addTutorRequest, MultipartFile file);
	
	Tutor findTutorById(Long id);
	void delete(long[] ids);
}
