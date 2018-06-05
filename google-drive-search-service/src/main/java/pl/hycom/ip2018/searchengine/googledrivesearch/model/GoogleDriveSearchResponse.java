package pl.hycom.ip2018.searchengine.googledrivesearch.model;

import com.google.gson.reflect.TypeToken;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

import java.lang.reflect.Type;

/**
 * Object representation of output
 */
public class GoogleDriveSearchResponse extends ProviderResponse{

    public static final Type TYPE = new TypeToken<GoogleDriveSearchResponse>() {}.getType();

    public static Type getTYPE() {
        return TYPE;
    }
}
