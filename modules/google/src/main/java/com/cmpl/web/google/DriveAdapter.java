package com.cmpl.web.google;

import java.io.IOException;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;

public interface DriveAdapter {

   void sendFilesToGoogleDrive(File fileToCreate, InputStreamContent input) throws IOException;

}
