package doancnpm.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.request.InvitationRequest;
import doancnpm.payload.request.StudentRequest;
import doancnpm.security.IInvitationService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class InvitationController {
	
	
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private IInvitationService invitationService;
	
	
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
	
	@PutMapping(value = "/invitation/accepted")
	@PreAuthorize("hasRole('TUTOR')")
	public void acceptInvitation(HttpServletRequest request, @RequestBody InvitationRequest model) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		
		invitationService.accept(username, model.getId());
	}
	
	@PutMapping(value = "/invitation/rejected")
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
