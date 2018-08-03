package com.cmpl.web.backup;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmpl.web.backup.reader.BackupImporter;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class BackupImporterJob implements Job {

  private static final Logger LOGGER = LoggerFactory.getLogger(BackupImporterJob.class);

  @Autowired
  private BackupImporter backupImporter;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    LOGGER.info("Debut du job d'import de backup");
    backupImporter.importBackup();
    LOGGER.info("Fin du job de backup");

  }

}
