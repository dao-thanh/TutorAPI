package doancnpm.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import doancnpm.converter.SuggestionConverter;
import doancnpm.models.Post;
import doancnpm.models.Suggestion;
import doancnpm.models.Tutor;
import doancnpm.payload.request.SuggestionRequest;
import doancnpm.repository.PostRepository;
import doancnpm.repository.SuggestionRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.security.iSuggestionService;

@Service
public class SuggestionService implements iSuggestionService {

	@Autowired
	private SuggestionRepository suggestionRepository;

	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private SuggestionConverter suggestionConverter;

	@Override
	public SuggestionRequest save(SuggestionRequest suggestionDTO) {
		Suggestion suggestionEntity = new Suggestion();
		if (suggestionDTO.getId() != null) {
			Suggestion oldInvitationEntity = suggestionRepository.findOne(suggestionDTO.getId());
			suggestionEntity = suggestionConverter.toEntity(suggestionDTO, oldInvitationEntity);
		} else {
			suggestionEntity = suggestionConverter.toEntity(suggestionDTO);
		}

		Post postEntity = postRepository.findOneById(suggestionDTO.getPostID());
		suggestionEntity.setPost(postEntity);
		Tutor tutorEntity = tutorRepository.findOneById(suggestionDTO.getTutorID());
		suggestionEntity.setTutor(tutorEntity);
		suggestionEntity = suggestionRepository.save(suggestionEntity);
		return suggestionConverter.toDTO(suggestionEntity);
	}

	@Override
	public void delete(long[] ids) {
		for (long item : ids) {
			suggestionRepository.delete(item);
		}

	}

	@Override
	public List<SuggestionRequest> findAll(Pageable pageable) {
		List<SuggestionRequest> results = new ArrayList<>();
		List<Suggestion> entities = suggestionRepository.findAll(pageable).getContent();
		for (Suggestion item : entities) {
			SuggestionRequest suggestionDTO = suggestionConverter.toDTO(item);
			results.add(suggestionDTO);
		}
		return results;
	}

	@Override
	public int totalItem() {
		return (int) suggestionRepository.count();
	}

}
