package lv.robertsv.webjob.dto;

public class Job {
	
	private Long id;

	private String path;
	
	private String schedule;
	
	public Job() {
		super();
	}

	public Job(Long id, String path, String schedule) {
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
