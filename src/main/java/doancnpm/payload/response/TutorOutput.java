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
	private String name;
	private String phonenumber;
	private String qualification;
	private String avatar;
	private String rating;
	private String description;
	private String address;
	@JsonIgnoreProperties("tutors")
	private Set<String> subjects;
	private Set<String> grades;

	private Map<String,Boolean> schedules;
	
	public Map<String, Boolean> getSchedules() {
		return schedules;
	}
	public void setSchedules(Map<String, Boolean> schedules) {
		this.schedules = schedules;
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
	
	
	public Set<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(Set<String> subjects) {
		this.subjects = subjects;
	}
	public Set<String> getGrades() {
		return grades;
	}
	public void setGrades(Set<String> grades) {
		this.grades = grades;
	}
	public String getName() {
		return name;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
}
