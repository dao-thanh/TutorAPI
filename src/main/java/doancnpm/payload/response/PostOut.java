package doancnpm.payload.response;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import doancnpm.models.Student;
import doancnpm.models.Subject;

public class PostOut {
	private Long id;
	private Long idStudent;
	private String title;
	@JsonIgnoreProperties("tutors")
	private String grade;
	@JsonIgnoreProperties("posts")
	private Set<String> subjects;
	private String price;
	private String phoneNumber;
	private String address;
	private String description;
	private Map<String,Boolean> schedule;
	
	
	public Long getIdStudent() {
		return idStudent;
	}
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Map<String, Boolean> getSchedule() {
		return schedule;
	}
	public void setSchedule(Map<String, Boolean> schedule) {
		this.schedule = schedule;
	}
	
	
}
