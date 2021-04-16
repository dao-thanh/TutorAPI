package doancnpm.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import javax.persistence.OneToMany;
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
	@JsonIgnoreProperties("tutor")
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
	
	
	@OneToMany(mappedBy = "tutor")
	@JsonIgnoreProperties("tutor")
	private List<Message> messages = new ArrayList<>();
	
	
	
//	@Column(name = "sang_2")
//	private boolean sang_2 = false;
//	@Column(name = "chieu_2")
//	private boolean chieu_2 = false;
//	@Column(name = "toi_2")
//	private boolean toi_2 = false;
//	
//	@Column(name = "sang_3")
//	private boolean sang_3 = false;
//	@Column(name = "chieu_3")
//	private boolean chieu_3 = false;
//	@Column(name = "toi_3")
//	private boolean toi_3 = false;
//	
//	@Column(name = "sang_4")
//	private boolean sang_4 = false;
//	@Column(name = "chieu_4")
//	private boolean chieu_4 = false;
//	@Column(name = "toi_4")
//	private boolean toi_4 = false;
//	
//	@Column(name = "sang_5")
//	private boolean sang_5 = false;
//	@Column(name = "chieu_5")
//	private boolean chieu_5 = false;
//	@Column(name = "toi_5")
//	private boolean toi_5 = false;
//	
//	@Column(name = "sang_6")
//	private boolean sang_6 = false;
//	@Column(name = "chieu_6")
//	private boolean chieu_6 = false;
//	@Column(name = "toi_6")
//	private boolean toi_6 = false;
//	
//	@Column(name = "sang_7")
//	private boolean sang_7 = false;
//	@Column(name = "chieu_7")
//	private boolean chieu_7 = false;
//	@Column(name = "toi_7")
//	private boolean toi_7 = false;
//	
//	@Column(name = "sang_8")
//	private boolean sang_8 = false;
//	@Column(name = "chieu_8")
//	private boolean chieu_8 = false;
//	@Column(name = "toi_8")
//	private boolean toi_8 = false;
	
	@Column(name="schedule")
	private String schedule;
	
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
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

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	

//	public boolean isSang_2() {
//		return sang_2;
//	}
//
//	public void setSang_2(boolean sang_2) {
//		this.sang_2 = sang_2;
//	}
//
//	public boolean isChieu_2() {
//		return chieu_2;
//	}
//
//	public void setChieu_2(boolean chieu_2) {
//		this.chieu_2 = chieu_2;
//	}
//
//	public boolean isToi_2() {
//		return toi_2;
//	}
//
//	public void setToi_2(boolean toi_2) {
//		this.toi_2 = toi_2;
//	}
//
//	public boolean isSang_3() {
//		return sang_3;
//	}
//
//	public void setSang_3(boolean sang_3) {
//		this.sang_3 = sang_3;
//	}
//
//	public boolean isChieu_3() {
//		return chieu_3;
//	}
//
//	public void setChieu_3(boolean chieu_3) {
//		this.chieu_3 = chieu_3;
//	}
//
//	public boolean isToi_3() {
//		return toi_3;
//	}
//
//	public void setToi_3(boolean toi_3) {
//		this.toi_3 = toi_3;
//	}
//
//	public boolean isSang_4() {
//		return sang_4;
//	}
//
//	public void setSang_4(boolean sang_4) {
//		this.sang_4 = sang_4;
//	}
//
//	public boolean isChieu_4() {
//		return chieu_4;
//	}
//
//	public void setChieu_4(boolean chieu_4) {
//		this.chieu_4 = chieu_4;
//	}
//
//	public boolean isToi_4() {
//		return toi_4;
//	}
//
//	public void setToi_4(boolean toi_4) {
//		this.toi_4 = toi_4;
//	}
//
//	public boolean isSang_5() {
//		return sang_5;
//	}
//
//	public void setSang_5(boolean sang_5) {
//		this.sang_5 = sang_5;
//	}
//
//	public boolean isChieu_5() {
//		return chieu_5;
//	}
//
//	public void setChieu_5(boolean chieu_5) {
//		this.chieu_5 = chieu_5;
//	}
//
//	public boolean isToi_5() {
//		return toi_5;
//	}
//
//	public void setToi_5(boolean toi_5) {
//		this.toi_5 = toi_5;
//	}
//
//	public boolean isSang_6() {
//		return sang_6;
//	}
//
//	public void setSang_6(boolean sang_6) {
//		this.sang_6 = sang_6;
//	}
//
//	public boolean isChieu_6() {
//		return chieu_6;
//	}
//
//	public void setChieu_6(boolean chieu_6) {
//		this.chieu_6 = chieu_6;
//	}
//
//	public boolean isToi_6() {
//		return toi_6;
//	}
//
//	public void setToi_6(boolean toi_6) {
//		this.toi_6 = toi_6;
//	}
//
//	public boolean isSang_7() {
//		return sang_7;
//	}
//
//	public void setSang_7(boolean sang_7) {
//		this.sang_7 = sang_7;
//	}
//
//	public boolean isChieu_7() {
//		return chieu_7;
//	}
//
//	public void setChieu_7(boolean chieu_7) {
//		this.chieu_7 = chieu_7;
//	}
//
//	public boolean isToi_7() {
//		return toi_7;
//	}
//
//	public void setToi_7(boolean toi_7) {
//		this.toi_7 = toi_7;
//	}
//
//	public boolean isSang_8() {
//		return sang_8;
//	}
//
//	public void setSang_8(boolean sang_8) {
//		this.sang_8 = sang_8;
//	}
//
//	public boolean isChieu_8() {
//		return chieu_8;
//	}
//
//	public void setChieu_8(boolean chieu_8) {
//		this.chieu_8 = chieu_8;
//	}
//
//	public boolean isToi_8() {
//		return toi_8;
//	}
//
//	public void setToi_8(boolean toi_8) {
//		this.toi_8 = toi_8;
//	}		
	
	
}
