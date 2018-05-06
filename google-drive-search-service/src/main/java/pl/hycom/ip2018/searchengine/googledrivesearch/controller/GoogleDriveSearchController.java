package pl.hycom.ip2018.searchengine.googledrivesearch.controller;

import com.google.api.services.drive.Drive;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.googledrivesearch.exception.GoogleDriveSearchException;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.GoogleDriveAuth;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.GoogleDriveSearch;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Class marked as controller, method returns json objects
 */
@RestController
public class GoogleDriveSearchController {

    @Autowired
    private GoogleDriveSearch googleDriveSearch;
    @Autowired
    private GoogleDriveAuth googleDriveAuth;

    /**
     * Endpoint for google drive search service
     * @param query phrase that is searched for
     * @return ProviderResponse
     * @throws GoogleDriveSearchException thrown in case of Internal Server Error
     */
    @HystrixCommand(fallbackMethod = "getResponseFromGoogleDriveFallBack",commandKey = "fallback", groupKey = "fallback")
    @RequestMapping(value = "/res/{query}", method = GET)
    public ProviderResponse getResponseFromGoogleDrive(@PathVariable String query) throws GoogleDriveSearchException {
        Drive drive = googleDriveAuth.getAuthDriveService();
        return googleDriveSearch.getResponseFromGoogleDriveByQuery(drive, query);
    }

    public ProviderResponse getResponseFromGoogleDriveFallBack(String query) {
        return new ProviderResponse(new ArrayList<>());
    }

}
