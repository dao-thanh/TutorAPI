package doancnpm.payload.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import doancnpm.models.Student;

public class PostOut {
	private Long id;
	private String address;
	private String subject;
	private String grade;
	private String title;
	private String description;
	private String price;
	private String phoneNumber;
	private Map<String,Boolean> schedule;
//	@JsonIgnoreProperties("student")
//	private Student student;
	private Long idStudent;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
//	public Student getStudent() {
//		return student;
//	}
//	public void setStudent(Student student) {
//		this.student = student;
//	}
	
	
}