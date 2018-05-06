package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.services.drive.Drive;

/**
 * Interface that specifies obtaining Google Drive auth.
 */
public interface GoogleDriveAuth {

    /**
     * Before submitting a query it is necessary
     * to build an authorized API client service.
     * @return Drive service that manages files in Google Drive
     */
    Drive getAuthDriveService();
}
