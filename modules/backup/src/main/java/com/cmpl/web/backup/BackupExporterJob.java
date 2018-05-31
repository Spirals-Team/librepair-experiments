package com.cmpl.web.backup;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmpl.web.backup.writer.ArchiveManager;
import com.cmpl.web.backup.writer.CSVGenerator;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class BackupExporterJob implements Job {

  private static final Logger LOGGER = LoggerFactory.getLogger(BackupExporterJob.class);

  @Autowired
  private CSVGenerator csvGenerator;

  @Autowired
  private ArchiveManager archiveManager;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    LOGGER.info("Debut du job de backup");
    LOGGER.info("Extraction des donnees vers des fichiers CSV");
    csvGenerator.extractAllDataToCSVFiles();
    LOGGER.info("Archivage des fichiers CSV");
    archiveManager.archiveData();
    LOGGER.info("Fin du job de backup");

  }

}
