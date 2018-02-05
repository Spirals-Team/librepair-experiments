package io.descoped.client.external.posten;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.exception.APIClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * https://www.bring.no/radgivning/sende-noe/adressetjenester/postnummer
 * https://www.bring.no/radgivning/sende-noe/adressetjenester/postnummer/postnummertabeller-veiledning
 *
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class PostenPostalCodesClient {

    private static final Logger log = LoggerFactory.getLogger(PostenPostalCodesClient.class);
    protected static String SAFE_SPLIT_TABBED_REGEXP = "\t(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    private static String POSTEN_URL = "https://www.bring.no/postnummerregister-ansi.txt";
    private Map<String, PostalCode> internalMap = new LinkedHashMap<>();

    public PostenPostalCodesClient() {
    }

    public Map<String, PostalCode> fetch() throws Exception {
        HttpRequest req = HttpRequest.get(POSTEN_URL);
        if (!req.ok()) throw new APIClientException("Error fetching postnr db: " + req.code());

        File file = File.createTempFile("posten", ".tab");
        log.trace("Download file: {}", file.getAbsolutePath());
        req.receive(file);
        log.trace("Download completed!");

        String rowLine;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"))) {
            while ((rowLine = br.readLine()) != null) {
                PostalCode postalCode = PostalCode.valueOf(rowLine);
                internalMap.put(postalCode.getCode(), postalCode);
            }
        }
        return internalMap;
    }

    public Map<String,PostalCode> getMap() {
        return internalMap;
    }

    public Set<String> getUniquePlaces() {
        HashSet<String> set = new HashSet<>();
        internalMap.forEach((k, v) -> {
            set.add(v.getPlace());
        });
        return set;
    }

    public PostalCode getPostalCodeByCode(String postalCode) {
        return internalMap.get(postalCode);
    }
}
