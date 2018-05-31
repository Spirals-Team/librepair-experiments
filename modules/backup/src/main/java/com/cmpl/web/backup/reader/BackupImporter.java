package com.cmpl.web.backup.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class BackupImporter {

  private static final Logger LOGGER = LoggerFactory.getLogger(BackupImporter.class);

  private final CSVReader csvReader;
  private final String backupFilePath;
  private final String mediaFilePath;
  private final String pagesFilePath;
  private final String actualitesFilePath;

  private static final String DOT = ".";
  private static final String HTML_EXTENSION = "html";
  private static final long TEN_DAYS_MILLISECONDS = 10 * 24 * 60 * 60 * 1000;

  public BackupImporter(CSVReader csvReader, String backupFilePath, String mediaFilePath, String pagesFilePath,
      String actualitesFilePath) {
    this.csvReader = csvReader;
    this.backupFilePath = backupFilePath;
    this.mediaFilePath = mediaFilePath;
    this.pagesFilePath = pagesFilePath;
    this.actualitesFilePath = actualitesFilePath;
  }

  public void importBackup() {

    LOGGER.info("Debut de l'import des donnees");
    boolean hasData = false;
    try {
      hasData = unzipLatestIfExists();
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la decompression du backup ", e);
    }
    if (!hasData) {
      LOGGER.info("Rien a importer");
      return;
    }
    LOGGER.info("Lecture des fichiers et import");
    try {
      readCSVFilesAndImportData();
    } catch (Exception e) {
      LOGGER.error("Echec de l'import des donnees venant des fichiers csv");
    }
    try {
      importPagesFiles();
    } catch (Exception e) {
      LOGGER.error("Echec de l'import des pages");
    }
    try {
      importMediaFiles();
    } catch (Exception e) {
      LOGGER.error("Echec de l'import des media");
    }

    try {
      importActualitesFiles();
    } catch (Exception e) {
      LOGGER.error("Echec de l'import des actualites");
    }

    LOGGER.info("Suppression des fichiers desarchives");
    deleteUnzippedFiles();

    LOGGER.info("Suppression des fichiers plus vieux que dix jours");
    deleteOlderThanTenDaysFiles();

    LOGGER.info("Fin de l'import des donnees");

  }

  boolean unzipLatestIfExists() throws IOException {

    File backupFolder = new File(backupFilePath);
    LOGGER.info("Dossier de backup : " + backupFolder.getAbsolutePath());
    if (backupFolder.exists()) {
      File[] files = backupFolder.listFiles(file -> file.isFile());
      File latestBackup = computeLatestBackup(files);
      if (latestBackup != null) {
        LOGGER.info("Dernier backup : " + latestBackup);
        unzipBackup(latestBackup);
        return true;
      }

    }
    LOGGER.info("Dossier de backup inexistant ou pas de zip " + backupFolder.getAbsolutePath());
    return false;
  }

  void unzipBackup(File latestBackup) throws FileNotFoundException, IOException {
    ZipInputStream zis = new ZipInputStream(new FileInputStream(latestBackup));
    ZipEntry ze = zis.getNextEntry();
    byte[] buffer = new byte[1024];
    while (ze != null) {
      String fileName = ze.getName();
      File newFile = fetchFile(backupFilePath, fileName);
      writeZipEntryToFile(zis, buffer, newFile);
      ze = zis.getNextEntry();
    }

    zis.closeEntry();
    zis.close();
  }

  private void writeZipEntryToFile(ZipInputStream zis, byte[] buffer, File newFile) throws FileNotFoundException,
      IOException {
    FileOutputStream fos = new FileOutputStream(newFile);

    int len;
    while ((len = zis.read(buffer)) > 0) {
      fos.write(buffer, 0, len);
    }

    fos.close();
  }

  File computeLatestBackup(File[] files) {
    File latestBackup = null;
    long lastMod = Long.MIN_VALUE;
    for (File file : files) {
      if (file.lastModified() > lastMod) {
        latestBackup = file;
        lastMod = file.lastModified();
      }
    }
    return latestBackup;
  }

  void readCSVFilesAndImportData() {
    csvReader.extractAllDataFromCSVFiles();
  }

  void deleteUnzippedFiles() {
    List<File> filesToDelete = getCSVFiles();
    filesToDelete.stream().forEach(file -> file.delete());
  }

  void deleteOlderThanTenDaysFiles() {
    File directory = fetchFile(backupFilePath, null);
    if (!directory.exists()) {
      return;
    }
    List<File> filesInBackup = Arrays.asList(directory.listFiles());
    filesInBackup.stream().forEach(file -> deleteFileIfOlderThanTenDays(file));

  }

  void deleteFileIfOlderThanTenDays(File fileToExamine) {
    long diff = new Date().getTime() - fileToExamine.lastModified();

    if (diff > TEN_DAYS_MILLISECONDS) {
      fileToExamine.delete();
    }
  }

  List<File> getCSVFiles() {
    List<File> csvFiles = new ArrayList<>();
    File directory = fetchFile(backupFilePath, null);
    if (!directory.exists()) {
      return csvFiles;
    }
    csvFiles = Arrays.asList(directory.listFiles((dir, name) -> name.endsWith(".csv")));
    return csvFiles;
  }

  void importMediaFiles() {
    File backupDirectory = fetchFile(backupFilePath, "medias");
    List<File> mediaFiles = Arrays
        .asList(backupDirectory.listFiles((dir, name) -> !name.endsWith(DOT + HTML_EXTENSION)));
    mediaFiles.forEach(file -> moveFile(file, mediaFilePath));
  }

  void importPagesFiles() {
    File backupDirectory = fetchFile(backupFilePath, "pages");
    List<File> pageFiles = Arrays.asList(backupDirectory.listFiles());
    pageFiles.forEach(file -> moveFile(file, pagesFilePath));
  }

  void importActualitesFiles() {
    File backupDirectory = fetchFile(backupFilePath, "actualites");
    List<File> actualitesFiles = Arrays.asList(backupDirectory.listFiles());
    actualitesFiles.forEach(file -> moveFile(file, actualitesFilePath));
  }

  File fetchFile(String basePath, String fileName) {
    if (StringUtils.hasText(fileName)) {
      return new File(basePath + File.separator + fileName);
    }
    return new File(basePath);
  }

  void moveFile(File fileToMove, String basePath) {
    fileToMove.renameTo(new File(basePath + File.separator + fileToMove.getName()));
  }

}
