package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.domain.Chargeback;
import nl.stil4m.mollie.domain.Page;

/**
 *
 */
public class PaymentChargebacks extends AbstractConcept<Chargeback> implements ListAll<Chargeback>, GetById<Chargeback> {

    private static final TypeReference<Page<Chargeback>> PAGE_TYPE = new TypeReference<Page<Chargeback>>() {
    };
    private static final TypeReference<Chargeback> SINGLE_TYPE = new TypeReference<Chargeback>() {
    };

    public PaymentChargebacks(String apiKey, String endpoint, RequestExecutor requestExecutor, String paymentId) {
        super(apiKey, requestExecutor, SINGLE_TYPE, PAGE_TYPE, endpoint, "payments", paymentId, "chargebacks");
    }
}
