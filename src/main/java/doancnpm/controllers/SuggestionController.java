package doancnpm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.controllers.output.SuggestionOutput;
import doancnpm.payload.request.SuggestionRequest;
import doancnpm.repository.SuggestionRepository;
import doancnpm.security.iSuggestionService;

@CrossOrigin
@RestController
public class SuggestionController {

	@Autowired
	SuggestionRepository suggestionRepository;

	@Autowired
	private iSuggestionService suggestionService;

	@PostMapping(value = "/suggestion")
	public SuggestionRequest createSuggestion(@RequestBody SuggestionRequest model) {
		return suggestionService.save(model);
	}

	@PutMapping(value = "/suggestion/{id}")
	public SuggestionRequest updateSuggestion(@RequestBody SuggestionRequest model, @PathVariable("id") long id) {
		model.setId(id);
		return suggestionService.save(model);
	}

	@DeleteMapping(value = "/suggestion")
	public void deleteSuggestion(@RequestBody long[] ids) {
		suggestionService.delete(ids);
	}

	@GetMapping(value = "/suggestion")
	public SuggestionOutput showSuggestion(@RequestParam("page") int page, @RequestParam("limit") int limit) {
		SuggestionOutput result = new SuggestionOutput();
		result.setPage(page);
		Pageable pageable = new PageRequest(page - 1, limit);
		result.setListResultDTO(suggestionService.findAll(pageable));
		result.setTotalPage((int) Math.ceil((double) (suggestionService.totalItem()) / limit));
		return result;
	}
}
