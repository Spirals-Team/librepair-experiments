package pl.hycom.ip2018.searchengine.providercontract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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

    protected Map<String, String> additionalData = new HashMap<>();

    protected String url;

    public void addToAdditionalData(String key, String value) {
        additionalData.put(key, value);
    }
}
