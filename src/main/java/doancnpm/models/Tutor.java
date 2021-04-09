package doancnpm.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "tutor")
public class Tutor implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "qualification")
	private String qualification;

	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "rating")
	private String rating;
	

	@Column(name = "description")
	private String description;


	@Column(name = "address")
	private String address;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	//@OneToOne(fetch = FetchType.LAZY)
    //@MapsId
    private User user;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tutor_subject", joinColumns = @JoinColumn(name = "tutor_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	@JsonIgnoreProperties("tutors")
	private Set<Subject> subjects = new HashSet<>();

//	@ManyToOne
//	@JoinColumn(name="subjectId")
//	@JsonIgnoreProperties("tutors")
//	private Subject subject; 
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tutor_grade", joinColumns = @JoinColumn(name = "tutor_id"), inverseJoinColumns = @JoinColumn(name = "grade_id"))
	@JsonIgnoreProperties("tutors")
	private Set<Grade> grades = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tutor_schedule", joinColumns = @JoinColumn(name = "tutor_id"), inverseJoinColumns = @JoinColumn(name = "schedule_id"))
	@JsonIgnoreProperties("tutors")
	private Set<Schedule> schedules = new HashSet<>();
	
	
	
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
	
	public Set<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public Set<Grade> getGrades() {
		return grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}		
	
}
