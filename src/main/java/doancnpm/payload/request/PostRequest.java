package doancnpm.payload.request;

import java.util.Date;
import java.util.Map;

public class PostRequest extends BaseRequest<PostRequest> {

	private String student;
	private String title;
	private String description;
	private String subject;
	private String grade;
	private String price;
	private String phoneNumber;
	private String address;
	//private Long studentID;
	private Map<String, Boolean> schedule;

//	public Long getStudentID() {
//		return studentID;
//	}
//
//	public void setStudentID(Long studentID) {
//		this.studentID = studentID;
//	}

	public Map<String, Boolean> getSchedule() {
		return schedule;
	}

	public void setSchedule(Map<String, Boolean> schedule) {
		this.schedule = schedule;
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
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

	private Date createdDate;

	private Date modifiedDate;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



}
