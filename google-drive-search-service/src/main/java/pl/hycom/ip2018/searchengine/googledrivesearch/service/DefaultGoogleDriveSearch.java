package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.hycom.ip2018.searchengine.googledrivesearch.exception.GoogleDriveSearchException;
import pl.hycom.ip2018.searchengine.googledrivesearch.model.GoogleDriveSearchResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Implementation of {@link GoogleDriveSearch} to get data by query
 */
@Slf4j
public class DefaultGoogleDriveSearch implements GoogleDriveSearch {

    @Autowired
    private JsonResponse jsonResponse;

    @Autowired
    private ResponsePropertiesExtractor responsePropertiesExtractor;

    @Autowired
    private Environment environment;

    /**
     * Returns response wrapped in our type
     * @param service Drive service that manages files in Drive
     * @param query user searched phrase
     * @return GoogleDriveSearchResponse
     * @throws GoogleDriveSearchException thrown in case of Internal Server Error
     */
    @Override
    public GoogleDriveSearchResponse getResponseFromGoogleDriveByQuery(Drive service, String query) throws GoogleDriveSearchException {
        if (log.isInfoEnabled()) {
            log.info("Requesting searching results for {}", query);
        }

        GoogleDriveSearchResponse result;
        try {
            FileList fileList = listFiles(service, query);
            Map simpleMap = responsePropertiesExtractor.makeSimpleMapFromFileList(service, fileList);
            String fromSimpleMapToJson = jsonResponse.getAsString(simpleMap);
            result = jsonResponse.getAsObject(fromSimpleMapToJson, GoogleDriveSearchResponse.TYPE);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Searching results for {} are not available from Google Drive", query);
            }
            throw new GoogleDriveSearchException();
        }

        return result;
    }

    private FileList listFiles(Drive service, String queryWord) {
        FileList result = null;
        try {
            String q = "fullText contains '%s'";
            result = service.files().list()
                    .setPageSize(Integer.getInteger(environment.getProperty("rest.api.size")))
                    .setFields(environment.getProperty("rest.api.fields"))
                    .setQ(String.format(q, queryWord))
                    .execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
