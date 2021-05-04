package doancnpm.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post")
public class Post  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	
	@ManyToOne
	@JoinColumn(name="grade_id")
	@JsonIgnoreProperties("posts")
	private Grade grade;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "post_subject", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	@JsonIgnoreProperties("posts")
	private Set<Subject> subjects = new HashSet<>();
	
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Suggestion> getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(List<Suggestion> suggestion) {
		this.suggestion = suggestion;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "price")
	private String price;

	@Column(name = "phoneNumber")
	private String phoneNumber;

	@Column(name = "address")
	private String address;

	@Column(name = "schedule")
	private String schedule;

	@ManyToOne
	@JoinColumn(name = "studentId")
	@JsonIgnoreProperties("post")
	private Student student;

	@OneToMany(mappedBy = "post")
	@JsonIgnoreProperties("post")
	private List<Suggestion> suggestion = new ArrayList<>();


	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Column(name = "createdDate")
	@CreatedDate
	private Date createdDate;

	@Column(name = "modifiedDate")
	@LastModifiedDate
	private Date modifiedDate;

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Post() {

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

	@Override
	public String toString() {
		return "Tutor [id=" + id + ", title=" + title + ", description=" + description + ", grade=" + grade
				+ ", price=" + price + ",phone=" + phoneNumber + ",address=" + address + "]";
	}

//	public Post(String title, String description, String grade, String subject, String price, String phoneNumber,
//			String address) {
//		super();
//		this.title = title;
//		this.description = description;
//		this.grade = grade;
//		this.subject = subject;
//		this.price = price;
//		this.phoneNumber = phoneNumber;
//		this.address = address;
//	}

}
