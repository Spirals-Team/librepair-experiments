package com.cmpl.web.backup.writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.cmpl.web.google.DriveAdapter;
import com.google.api.client.http.InputStreamContent;

public class ArchiveManagerImpl implements ArchiveManager {

  private final String backupFilePath;
  private final String mediaFilePath;
  private final String pagesFilePath;
  private final String actualitesFilePath;
  private final DateTimeFormatter dateTimeFormatter;
  private final DriveAdapter driveAdapter;

  private static final String DOT = ".";
  private static final String CSV_EXTENSION = "csv";
  private static final String ZIP_EXTENSION = "zip";
  private static final long TEN_DAYS_MILLISECONDS = 10 * 24 * 60 * 60 * 1000;

  private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveManagerImpl.class);

  public ArchiveManagerImpl(String backupFilePath, String mediaFilePath, String pagesFilePath,
      String actualitesFilePath, DriveAdapter driveAdapter) {
    this.backupFilePath = backupFilePath;
    this.mediaFilePath = mediaFilePath;
    this.pagesFilePath = pagesFilePath;
    this.actualitesFilePath = actualitesFilePath;
    this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    this.driveAdapter = driveAdapter;
  }

  @Override
  public void archiveData() {

    LOGGER.info("Recuperation des fichiers a archiver");
    List<File> csvFiles = getCSVFiles();
    List<File> pagesFiles = getPagesFiles();
    List<File> mediaFiles = getMediaFiles();
    List<File> actualitesFiles = getActualitesFiles();

    LOGGER.info("Archivage des fichiers");
    File zipFile = zipFiles(csvFiles, pagesFiles, mediaFiles, actualitesFiles);
    if (!CollectionUtils.isEmpty(csvFiles)) {
      LOGGER.info("Suppression des fichiers CSV");
      deleteCSVFiles(csvFiles);
    }
    if (zipFile != null && zipFile.exists()) {
      copyZipToGoogleDrive(zipFile);
    }
    LOGGER.info("Suppression des backup de plus de 10 jours");
    deleteOlderThanTenDaysFiles();

  }

  private List<File> getCSVFiles() {
    List<File> csvFiles = new ArrayList<>();
    File directory = new File(backupFilePath);
    if (!directory.exists()) {
      return csvFiles;
    }
    csvFiles = Arrays.asList(directory.listFiles((dir, name) -> name.endsWith(DOT + CSV_EXTENSION)));
    return csvFiles;
  }

  private List<File> getMediaFiles() {
    List<File> mediaFiles = new ArrayList<>();
    File directory = new File(mediaFilePath);
    if (!directory.exists()) {
      return mediaFiles;
    }
    mediaFiles = Arrays.asList(directory.listFiles(file -> !file.isDirectory()));
    return mediaFiles;
  }

  private List<File> getActualitesFiles() {
    List<File> actualitesFiles = new ArrayList<>();
    File directory = new File(actualitesFilePath);
    if (!directory.exists()) {
      return actualitesFiles;
    }
    actualitesFiles = Arrays.asList(directory.listFiles());
    return actualitesFiles;
  }

  private List<File> getPagesFiles() {
    List<File> pagesFiles = new ArrayList<>();
    File directory = new File(pagesFilePath);
    if (!directory.exists()) {
      return pagesFiles;
    }
    pagesFiles = Arrays.asList(directory.listFiles());
    return pagesFiles;
  }

  private File zipFiles(List<File> csvFiles, List<File> pagesFiles, List<File> mediaFiles, List<File> actualitesFiles) {
    File directory = new File(backupFilePath);

    if (directory.exists()) {
      try {
        String zipFile = backupFilePath + File.separator + "backup_web_"
            + LocalDateTime.now().format(dateTimeFormatter) + DOT + ZIP_EXTENSION;
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);
        if (!CollectionUtils.isEmpty(csvFiles)) {
          csvFiles.stream().forEach(file -> zipCSVFile(zos, file));
        }
        if (!CollectionUtils.isEmpty(pagesFiles)) {
          pagesFiles.stream().forEach(file -> zipPageFile(zos, file));
        }
        if (!CollectionUtils.isEmpty(mediaFiles)) {
          mediaFiles.stream().forEach(file -> zipMediaFile(zos, file));
        }
        if (!CollectionUtils.isEmpty(actualitesFiles)) {
          actualitesFiles.stream().forEach(file -> zipActualiteFile(zos, file));
        }
        zos.closeEntry();
        zos.close();
        return new File(zipFile);
      } catch (Exception e) {
        LOGGER.info("Erreur lors de l'archivage des fichiers", e);
      }

    }
    return null;

  }

  private void zipCSVFile(ZipOutputStream zos, File fileToZip) {
    try {
      byte[] buffer = new byte[1024];
      ZipEntry ze = new ZipEntry(fileToZip.getName());
      zos.putNextEntry(ze);
      FileInputStream in = new FileInputStream(fileToZip);
      int len;
      while ((len = in.read(buffer)) > 0) {
        zos.write(buffer, 0, len);
      }
      in.close();
    } catch (Exception e) {
      LOGGER.info("Erreur lors de l'archivage du fichier " + fileToZip.getName(), e);

    }

  }

  private void zipMediaFile(ZipOutputStream zos, File fileToZip) {
    try {
      byte[] buffer = new byte[1024];
      ZipEntry ze = new ZipEntry(File.separator + "media" + File.separator + fileToZip.getName());
      zos.putNextEntry(ze);
      FileInputStream in = new FileInputStream(fileToZip);
      int len;
      while ((len = in.read(buffer)) > 0) {
        zos.write(buffer, 0, len);
      }
      in.close();
    } catch (Exception e) {
      LOGGER.info("Erreur lors de l'archivage du fichier " + fileToZip.getName(), e);

    }

  }

  private void zipPageFile(ZipOutputStream zos, File fileToZip) {
    try {
      byte[] buffer = new byte[1024];
      ZipEntry ze = new ZipEntry(File.separator + "pages" + File.separator + fileToZip.getName());
      zos.putNextEntry(ze);
      FileInputStream in = new FileInputStream(fileToZip);
      int len;
      while ((len = in.read(buffer)) > 0) {
        zos.write(buffer, 0, len);
      }
      in.close();
    } catch (Exception e) {
      LOGGER.info("Erreur lors de l'archivage du fichier " + fileToZip.getName(), e);

    }

  }

  private void zipActualiteFile(ZipOutputStream zos, File fileToZip) {
    try {
      byte[] buffer = new byte[1024];
      ZipEntry ze = new ZipEntry(File.separator + "actualites" + File.separator + fileToZip.getName());
      zos.putNextEntry(ze);
      FileInputStream in = new FileInputStream(fileToZip);
      int len;
      while ((len = in.read(buffer)) > 0) {
        zos.write(buffer, 0, len);
      }
      in.close();
    } catch (Exception e) {
      LOGGER.info("Erreur lors de l'archivage du fichier " + fileToZip.getName(), e);

    }

  }

  private void deleteCSVFiles(List<File> filesToDelete) {
    filesToDelete.stream().forEach(file -> file.delete());
  }

  private void deleteOlderThanTenDaysFiles() {
    File directory = new File(backupFilePath);
    if (!directory.exists()) {
      return;
    }
    List<File> filesInBackup = Arrays.asList(directory.listFiles());
    filesInBackup.stream().forEach(file -> deleteFileIfOlderThanTenDays(file));

  }

  private void deleteFileIfOlderThanTenDays(File fileToExamine) {
    long diff = new Date().getTime() - fileToExamine.lastModified();

    if (diff > TEN_DAYS_MILLISECONDS) {
      fileToExamine.delete();
    }
  }

  private void copyZipToGoogleDrive(File zipFile) {
    try {
      com.google.api.services.drive.model.File fileToCreate = new com.google.api.services.drive.model.File();
      fileToCreate.setDescription("Backup file");
      fileToCreate.setMimeType("application/zip");
      fileToCreate.setName(zipFile.getName());
      InputStreamContent input = new InputStreamContent("*/*", new FileInputStream(zipFile));
      driveAdapter.sendFilesToGoogleDrive(fileToCreate, input);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation dans le drive", e);
    }
  }
}
