package doancnpm.security;

import java.util.List;

import doancnpm.models.Comment;
import doancnpm.payload.request.CommentRequest;

public interface ICommentService {
	
	List<Comment> findByIdTutor(long idTutor);

	void save(String username, CommentRequest commentRequest);

}
