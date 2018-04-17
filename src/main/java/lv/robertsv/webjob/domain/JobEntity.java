package lv.robertsv.webjob.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JobEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String path;
	
	private String schedule;
	
	
	
	public JobEntity() {
		super();
	}

	public JobEntity(Long id, String path, String schedule) {
		super();
		this.id = id;
		this.path = path;
		this.schedule = schedule;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

}
