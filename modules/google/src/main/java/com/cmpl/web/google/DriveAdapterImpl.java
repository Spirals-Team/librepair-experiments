package com.cmpl.web.google;

import java.io.IOException;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

public class DriveAdapterImpl implements DriveAdapter {

  private final Drive driveService;

  public DriveAdapterImpl(Drive driveService) {
    this.driveService = driveService;
  }

  @Override
  public void sendFilesToGoogleDrive(File fileToCreate, InputStreamContent input) throws IOException {
    driveService.files().create(fileToCreate, input).execute();
  }

}
