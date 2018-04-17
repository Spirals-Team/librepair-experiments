package lv.robertsv.webjob.dto;

public class JobStatus {

	public static enum ExecutionStatus {
		RUNNING, FAILED, SUCCESS;
	}

	private long jobId;

	private ExecutionStatus status;

	public JobStatus(long jobId, ExecutionStatus status) {
		this.jobId = jobId;
		this.status = status;
	}

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public ExecutionStatus getStatus() {
		return status;
	}

	public void setStatus(ExecutionStatus status) {
		this.status = status;
	}

}
