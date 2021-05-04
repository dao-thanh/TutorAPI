package doancnpm.payload.response;

public class SuggestionResponse {

	private Long id;
	private Long idStudent;
	private Long idTutor;
	private Long idPost;
	public Long getIdPost() {
		return idPost;
	}
	public void setIdPost(Long idPost) {
		this.idPost = idPost;
	}
	private int status;
	
	public Long getId() {
		return id;
	}
	public Long getIdStudent() {
		return idStudent;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}
	public Long getIdTutor() {
		return idTutor;
	}
	public int getStatus() {
		return status;
	}
	public void setIdTutor(Long idTutor) {
		this.idTutor = idTutor;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
