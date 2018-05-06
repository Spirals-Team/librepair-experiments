package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.services.drive.Drive;
import pl.hycom.ip2018.searchengine.googledrivesearch.exception.GoogleDriveSearchException;
import pl.hycom.ip2018.searchengine.googledrivesearch.model.GoogleDriveSearchResponse;

/**
 * Interface specifying requesting Google Drive API.
 */
public interface GoogleDriveSearch {

    /**
     * Returns response wrapped in our type
     * @param service Drive service that manages files in Drive
     * @param query user searched phrase
     * @return GoogleDriveSearchResponse
     * @throws GoogleDriveSearchException thrown in case of Internal Server Error
     */
    GoogleDriveSearchResponse getResponseFromGoogleDriveByQuery(Drive service, String query) throws GoogleDriveSearchException;
}
