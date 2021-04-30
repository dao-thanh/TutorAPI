package doancnpm.security;

import doancnpm.payload.request.InvitationRequest;

public interface IInvitationService {
	void save(String username, Long idTutor);
	void accept(String username, long idStudent);
	void reject(String username, long idStudent);
}
