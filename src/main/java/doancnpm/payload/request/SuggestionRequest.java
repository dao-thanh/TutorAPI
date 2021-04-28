package doancnpm.payload.request;

public class SuggestionRequest extends BaseRequest<SuggestionRequest> {
	
	private Boolean status;
	private Long postID;
	private Long tutorID;
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Long getPostID() {
		return postID;
	}
	public void setPostID(Long postID) {
		this.postID = postID;
	}
	public Long getTutorID() {
		return tutorID;
	}
	public void setTutorID(Long tutorID) {
		this.tutorID = tutorID;
	}
	

}
