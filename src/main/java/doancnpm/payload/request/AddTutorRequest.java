package doancnpm.payload.request;


import java.util.Set;



public class AddTutorRequest {
	private String qualification;
	private String avatar;
	private String description;
	private Set<String> subjects;
	private Set<String> grade;
	private String address;
	private Set<String> teachingDate;
	private String username;
	private String rating;
	
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Set<String> getTeachingDate() {
		return teachingDate;
	}
	public void setTeachingDate(Set<String> teachingDate) {
		this.teachingDate = teachingDate;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

	public Set<String> getGrade() {
		return grade;
	}
	public void setGrade(Set<String> grade) {
		this.grade = grade;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Set<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(Set<String> subjects) {
		this.subjects = subjects;
	}
		
	
	
}
