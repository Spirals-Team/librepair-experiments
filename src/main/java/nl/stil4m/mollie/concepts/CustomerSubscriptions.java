package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.domain.CreateSubscription;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Subscription;

public class CustomerSubscriptions extends AbstractConcept<Subscription> implements ListAll<Subscription>, GetById<Subscription>, Create<Subscription, CreateSubscription>, DeleteWithResponse<Subscription> {

    private static final TypeReference<Page<Subscription>> PAGE_TYPE = new TypeReference<Page<Subscription>>() {
    };
    private static final TypeReference<Subscription> SINGLE_TYPE = new TypeReference<Subscription>() {
    };

    public CustomerSubscriptions(String apiKey, String endpoint, RequestExecutor requestExecutor, String customerId) {
        super(apiKey, requestExecutor, SINGLE_TYPE, PAGE_TYPE, endpoint, "customers", customerId, "subscriptions");
    }
}
