package doancnpm.security.services;

import java.io.IOException;
<<<<<<< HEAD
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
=======
import java.util.HashSet;
import java.util.List;
import java.util.Map;
>>>>>>> thanh
import java.util.Set;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.StandardCopyOption;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.converter.TutorConverter;
import doancnpm.models.Grade;
<<<<<<< HEAD

=======
>>>>>>> thanh
import doancnpm.models.Subject;

import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.response.FileStorageException;
import doancnpm.repository.GradeRepository;
<<<<<<< HEAD

=======
>>>>>>> thanh
import doancnpm.repository.SubjectRepository;

import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ITutorService;

@Service
public class TutorService implements ITutorService {

<<<<<<< HEAD
	private final Path fileStorageLocation;
=======

>>>>>>> thanh

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

<<<<<<< HEAD
	@Autowired
     public TutorService(Tutor tutor) {

         this.fileStorageLocation = Paths.get(tutor.getUploadDir())
        		 .toAbsolutePath().normalize();
         try {
             Files.createDirectories(this.fileStorageLocation);

         } catch (Exception ex) {
             throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
         }

     }
=======
>>>>>>> thanh
	
	@Override
	public String storeFile(MultipartFile file) {
		 // Normalize file name
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());        
	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            // Copy file to the target location (Replacing existing file with the same name)
	            Path targetLocation = this.fileStorageLocation.resolve(fileName);
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	            return fileName;
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	  }

	@Override
	public List<Tutor> findAll(String fileName) {
		try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
		return tutorRepository.findAll();
	}

	@Override
	public Tutor findTutorById(Long id) {

		return tutorRepository.findOne(id);
	}

	
	@Override
<<<<<<< HEAD
	public void save(AddTutorRequest addTutorRequest, MultipartFile file) {
=======
	public void save(AddTutorRequest addTutorRequest) {
>>>>>>> thanh

		User user = userRepository.findOneByusername(addTutorRequest.getUsername());

		Set<String> strSubject = addTutorRequest.getSubjects();
		Set<Subject> subjects = new HashSet<>();
		strSubject.forEach(subject -> {
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

		strGrade.forEach(grade -> {
			switch (grade) {
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

		Map<String, Boolean> schedule = addTutorRequest.getSchedule();
//		 try {
//	            String jsonResp = mapperObj.writeValueAsString(inputMap);
//	            System.out.println(jsonResp);
//	        } catch (IOException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }

		Tutor tutor = new Tutor();

		if (addTutorRequest.getId() != null) {
			Tutor oldTutor = tutorRepository.findOne(addTutorRequest.getId());
			tutor = tutorConverter.toTutor(addTutorRequest, oldTutor);
		} else {
			tutor = tutorConverter.toTutor(addTutorRequest);
		}
		// Tutor tutor = tutorConverter.toTutor(addTutorRequest);
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
<<<<<<< HEAD
		String fileName = storeFile(file);
		tutor.setAvatar(fileName);
=======
		
>>>>>>> thanh
		tutor.setSchedule(jsonResp);
		tutor.setUser(user);
		tutor = tutorRepository.save(tutor);
	}

	@Override
	public void delete(long[] ids) {
		for (long item : ids) {
			tutorRepository.delete(item);
		}

	}


<<<<<<< HEAD
=======

>>>>>>> thanh
}
