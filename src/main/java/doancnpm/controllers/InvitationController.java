package doancnpm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.models.Invitation;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.InvitationRequest;
import doancnpm.payload.response.InvitationResponse;
import doancnpm.payload.response.TutorOutput;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.IInvitationService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class InvitationController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private IInvitationService invitationService;
	
	@GetMapping(value = "/invitation")
	@PreAuthorize("hasRole('TUTOR')")
	public Map<String, List<InvitationResponse>> getInvitationByIdTutor(HttpServletRequest request) {
		
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);
		Tutor tutor = tutorRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Tutor Not Found"));
		List<Invitation> invitations = invitationService.findByIdTutor(tutor.getId());
		
		List<InvitationResponse> invitationResponses = new ArrayList<InvitationResponse>();
		for (int i = 0; i < invitations.size(); i++) {
			InvitationResponse invitationResponse = new InvitationResponse();
			invitationResponse.setId(invitations.get(i).getId());
			invitationResponse.setIdStudent(invitations.get(i).getStudent().getId());
			invitationResponse.setIdTutor(invitations.get(i).getTutor().getId());
			invitationResponse.setStatus(invitations.get(i).getStatus());
			invitationResponses.add(invitationResponse);
		}
		Map<String, List<InvitationResponse>> response = new HashMap<String, List<InvitationResponse>>();
		response.put("invitations", invitationResponses);
		return response;
	}
	
	@PostMapping(value = "/invitation")
	@PreAuthorize("hasRole('STUDENT')")
	public void createInvitation(HttpServletRequest request, @RequestParam long id) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		invitationService.save(username, id);
	}
	
	@PutMapping(value = "/invitation/acceptance")
	@PreAuthorize("hasRole('TUTOR')")
	public void acceptInvitation(HttpServletRequest request, @RequestBody InvitationRequest model) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		
		invitationService.accept(username, model.getId());
	}
	
	@PutMapping(value = "/invitation/denial")
	@PreAuthorize("hasRole('TUTOR')")
	public void rejectInvitation(HttpServletRequest request, @RequestBody InvitationRequest model) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		
		invitationService.reject(username, model.getId());
	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
}
