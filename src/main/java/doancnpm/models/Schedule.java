package doancnpm.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Schedule")
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	@Column(name = "teachingDate")
	private String teachingDate;
	
	@ManyToOne
	@JoinColumn(name="timeId")
	@JsonIgnoreProperties("schedules")
	private Time time;
	
//	@OneToMany(mappedBy = "schedule")
//	@JsonIgnoreProperties("schedule")
//	private List<Tutor> tutors = new ArrayList<>();
	
	@ManyToMany(mappedBy = "schedules")
	@JsonIgnoreProperties("schedules")
	private List<Tutor> tutors = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeachingDate() {
		return teachingDate;
	}

	public void setTeachingDate(String teachingDate) {
		this.teachingDate = teachingDate;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public List<Tutor> getTutors() {
		return tutors;
	}

	public void setTutors(List<Tutor> tutors) {
		this.tutors = tutors;
	}
	
	
}
