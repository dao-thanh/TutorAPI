package doancnpm.security;

import java.util.List;

import org.springframework.data.domain.Pageable;

import doancnpm.payload.request.SuggestionRequest;

public interface iSuggestionService {

	SuggestionRequest save(SuggestionRequest suggestionDTO);

	void delete(long[] ids);

	List<SuggestionRequest> findAll(Pageable pageable);

	int totalItem();
}
