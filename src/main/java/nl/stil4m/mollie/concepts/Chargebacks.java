package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.domain.Chargeback;
import nl.stil4m.mollie.domain.Page;

/**
 *
 * @author stevensnoeijen
 */
public class Chargebacks extends AbstractConcept<Chargeback> implements ListAll<Chargeback> {

    private static final TypeReference<Page<Chargeback>> PAGE_TYPE = new TypeReference<Page<Chargeback>>() {
    };
    private static final TypeReference<Chargeback> SINGLE_TYPE = new TypeReference<Chargeback>() {
    };

    public Chargebacks(String apiKey, String endPoint, RequestExecutor requestExecutor) {
        super(apiKey, requestExecutor, SINGLE_TYPE, PAGE_TYPE, endPoint, "chargebacks");
    }
}
