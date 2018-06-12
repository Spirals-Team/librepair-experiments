/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.kernel.batch;

import io.github.fasset.fasset.kernel.notifications.FileUploadNotification;
import io.github.fasset.fasset.kernel.subscriptions.AbstractSubscriber;
import io.github.fasset.fasset.kernel.subscriptions.Subscriber;
import io.github.fasset.fasset.kernel.subscriptions.Update;
import io.github.fasset.fasset.kernel.util.BatchJobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * This object bootstraps an excel upload job after the file has been uploaded to the server
 */
@Component("excelUploadJob")
public class ExcelUploadJob extends AbstractSubscriber implements Subscriber {

    private static final Logger log = LoggerFactory.getLogger(ExcelUploadJob.class);

    private final JobLauncher jobLauncher;

    private final Job importExcelJob;

    @Autowired
    public ExcelUploadJob(JobLauncher jobLauncher, @Qualifier("importExcelJob") Job importExcelJob) {
        super(ExcelUploadJob.class.getName());
        this.jobLauncher = jobLauncher;
        this.importExcelJob = importExcelJob;
    }

    /**
     * Systematically processes connect that can be extracted from an excel file provided as argument
     *
     * @param filePath from which we are reading the business domain connect
     */
    private void uploadExcelFile(String filePath, String month) throws BatchJobExecutionException {

        log.info("Uploading excel file on the path : {}", filePath);

        JobParameters jobParameters = new JobParametersBuilder().addString("fileName", filePath).addString("month", month).addString("time", LocalDateTime.now().toString()).toJobParameters();

        try {
            jobLauncher.run(importExcelJob, jobParameters);
        } catch (Throwable e) {

            String message = String.format("Exception encountered %s caused by %s,while launching job" + "nomenclature %s, reading from excel file : %s at time %s", e.getMessage(), e.getCause(),
                importExcelJob.getName(), jobParameters.getString("fileName"), jobParameters.getString("time"));

            throw new BatchJobExecutionException(message, e);

        }

    }

    /**
     * Listens for messages from the queue
     *
     * @param message Containing parameters of the file just uploaded
     */
    //@JmsListener(destination = "fileUploads", containerFactory = "messageFactory")
    private void listenForMessages(FileUploadNotification message) {

        String fileName = message.getFileName();
        String month = message.getMonth();

        log.debug("File : {} has been received on the server side and is about to be actioned", fileName);

        try {
            uploadExcelFile(fileName, month);
        } catch (BatchJobExecutionException e) {
            log.error("Exception encountered while uploading excel file from : {}", fileName);
        }
    }

    @Override
    protected void consumeUpdate(Update update) {

        FileUploadNotification message = (FileUploadNotification) update.getPayload();

        log.debug("File upload : {} has been received by the excelUploadJob for processing", message);

        listenForMessages(message);
    }
}
