package doancnpm.converter;

import org.springframework.stereotype.Component;

import doancnpm.models.Suggestion;
import doancnpm.payload.request.SuggestionRequest;

@Component
public class SuggestionConverter {

	public Suggestion toEntity(SuggestionRequest dto) {
		Suggestion entity = new Suggestion();
		entity.setStatus(dto.getStatus());
		return entity;
	}

	public SuggestionRequest toDTO(Suggestion entity) {
		SuggestionRequest dto = new SuggestionRequest();
		if (entity.getId() != null) {
			dto.setId(entity.getId());
		}
		dto.setStatus(entity.getStatus());
		return dto;
	}

	public Suggestion toEntity(SuggestionRequest dto, Suggestion entity) {
		entity.setStatus(dto.getStatus());
		return entity;
	}
}
