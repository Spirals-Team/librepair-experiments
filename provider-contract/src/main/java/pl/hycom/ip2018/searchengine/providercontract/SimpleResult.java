package pl.hycom.ip2018.searchengine.providercontract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Base class representing our model
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResult {

    protected String provider;

    protected String header;

    protected String snippet;

    protected String timestamp;

    protected String url;

}
