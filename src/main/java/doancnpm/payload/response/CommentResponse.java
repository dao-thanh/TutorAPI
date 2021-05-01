package doancnpm.payload.response;

public class CommentResponse {
	private Integer id;
	private Long idStudent;
	private Long idTutor;
	private String content;
	
	public Long getIdStudent() {
		return idStudent;
	}
	public Long getIdTutor() {
		return idTutor;
	}
	public String getContent() {
		return content;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}
	public void setIdTutor(Long idTutor) {
		this.idTutor = idTutor;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
