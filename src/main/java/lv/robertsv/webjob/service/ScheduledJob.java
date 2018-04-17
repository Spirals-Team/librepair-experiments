package lv.robertsv.webjob.service;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import lv.robertsv.webjob.WebJobsWebSocketConfig;
import lv.robertsv.webjob.dto.JobStatus;

@DisallowConcurrentExecution
@Component
public class ScheduledJob implements Job {

	public static enum JobParameters {
		JOB_ID, JOB_PATH, MSG_SRV;
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobData = context.getJobDetail().getJobDataMap();
		
		long jobId = jobData.getLong(JobParameters.JOB_ID.name());
		SimpMessagingTemplate messagingTemplate = (SimpMessagingTemplate) jobData.get(JobParameters.MSG_SRV.name());
		String jobPath = jobData.getString(JobParameters.JOB_PATH.name());
		ProcessBuilder processBuilder = new ProcessBuilder(jobPath);
		processBuilder.redirectErrorStream(true);
		processBuilder.redirectOutput(Redirect.INHERIT);
		try {
			messagingTemplate.convertAndSend(WebJobsWebSocketConfig.JOB_STATUS_URL, new JobStatus(jobId, JobStatus.ExecutionStatus.RUNNING));
			Process process = processBuilder.start();
			int exitStatus = process.waitFor();
			if (exitStatus == 0) {
				messagingTemplate.convertAndSend(WebJobsWebSocketConfig.JOB_STATUS_URL, new JobStatus(jobId, JobStatus.ExecutionStatus.SUCCESS));
			} else {
				messagingTemplate.convertAndSend(WebJobsWebSocketConfig.JOB_STATUS_URL, new JobStatus(jobId, JobStatus.ExecutionStatus.FAILED));
			}
		} catch (IOException | InterruptedException e) {
			messagingTemplate.convertAndSend(WebJobsWebSocketConfig.JOB_STATUS_URL, new JobStatus(jobId, JobStatus.ExecutionStatus.FAILED));
			throw new RuntimeException(e);
		}

	}

}
