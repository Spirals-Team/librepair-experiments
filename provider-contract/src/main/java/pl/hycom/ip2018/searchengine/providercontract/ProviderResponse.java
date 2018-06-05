package pl.hycom.ip2018.searchengine.providercontract;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderResponse {

    protected List<SimpleResult> results;

    protected Map<String, Object> metadata;

    public ProviderResponse(List<SimpleResult> results) {
        super();
        this.results = results;
    }
}
