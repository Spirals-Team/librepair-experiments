package pl.hycom.ip2018.searchengine.aggregate.intercomm;

import org.springframework.cloud.netflix.feign.FeignClient;
import pl.hycom.ip2018.searchengine.providercontract.service.ProviderSearch;


@FeignClient("google-drive-search-service")
public interface GoogleDriveClient extends ProviderSearch {
    //TODO
    // GoogleDrive musi dostosować się do contractu i wtedy jedziemy z paternem
}
