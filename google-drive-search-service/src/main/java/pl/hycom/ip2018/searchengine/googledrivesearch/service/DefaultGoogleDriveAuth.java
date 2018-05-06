package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import lombok.extern.slf4j.Slf4j;
import pl.hycom.ip2018.searchengine.googledrivesearch.GoogleDriveSearchApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

/**
 * Implementation of {@link GoogleDriveAuth} to obtain Google Drive auth.
 */
@Slf4j
public class DefaultGoogleDriveAuth implements GoogleDriveAuth{

    private static final String APPLICATION_NAME = "GoogleDriveSearchEngine\t";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static NetHttpTransport HTTP_TRANSPORT;
    private static final String SERVICE_ACCOUNT_SECRET = "/service_account_secret.json";

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Returns Drive service that manages files in Drive
     * including uploading, downloading, searching, detecting changes, and updating sharing permissions.
     * @return Drive
     */
    @Override
    public Drive getAuthDriveService() {
        Credential googleCredential = getCredential();
        if (log.isInfoEnabled()) {
            log.info("Building a new authorized API client service.");
        }
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleCredential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private Credential getCredential() {
        if (log.isInfoEnabled()) {
            log.info("Requesting google credentials");
        }
        GoogleCredential googleCredential = null;
        try {
            InputStream in = GoogleDriveSearchApplication.class.getResourceAsStream(SERVICE_ACCOUNT_SECRET);
            googleCredential = GoogleCredential.fromStream(in)
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("Could not load google credentials from {}", SERVICE_ACCOUNT_SECRET);
            }
            e.printStackTrace();
        }
        return googleCredential;
    }
}
