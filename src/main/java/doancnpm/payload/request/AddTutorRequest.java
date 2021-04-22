package doancnpm.payload.request;

import java.util.Map;
import java.util.Set;

public class AddTutorRequest {
	private Long id;
	private String qualification;
	private String description;
	private Set<String> subjects;
	private Set<String> grade;
	private String address;
	private String rating;
	private Map<String,Boolean> schedule;
	
	
//	public String getSchedule() {
//		return schedule;
//	}
//	public void setSchedule(String schedule) {
//		this.schedule = schedule;
//	}
	
	public Long getId() {
		return id;
	}
	public Map<String, Boolean> getSchedule() {
		return schedule;
	}
	public void setSchedule(Map<String, Boolean> schedule) {
		this.schedule = schedule;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}

	
	
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
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
