package doancnpm.payload.response;

import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import doancnpm.models.Grade;
import doancnpm.models.Subject;
import doancnpm.models.User;

public class TutorOutput {
	private Long id;
	private String qualification;
	private String avatar;
	private String rating;
	private String description;
	private String address;
	@JsonIgnoreProperties("tutor")
	private User user;
	@JsonIgnoreProperties("tutors")
	private Set<Subject> subjects;
	private Set<Grade> grades;

	private Map<String,Boolean> schedules;
	
	public Map<String, Boolean> getSchedules() {
		return schedules;
	}
	public void setSchedules(Map<String, Boolean> schedules) {
		this.schedules = schedules;
	}

	
	public Set<Grade> getGrades() {
		return grades;
	}
	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}
	public Long getId() {
		return id;
	}
	public String getQualification() {
		return qualification;
	}
	public String getAvatar() {
		return avatar;
	}
	public String getRating() {
		return rating;
	}
	public String getDescription() {
		return description;
	}
	public String getAddress() {
		return address;
	}
	public User getUser() {
		return user;
	}
	public Set<Subject> getSubjects() {
		return subjects;
	}

	
	public void setId(Long id) {
		this.id = id;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}
	
}
