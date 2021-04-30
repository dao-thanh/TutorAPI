package doancnpm.security;

import java.util.List;

import doancnpm.models.Comment;
import doancnpm.models.Invitation;
import doancnpm.models.Tutor;
import doancnpm.payload.request.CommentRequest;

public interface ICommentService {
	List<Comment> all();
	Comment getMessageById(long id);
	void save(String username, CommentRequest commentRequest);

}
