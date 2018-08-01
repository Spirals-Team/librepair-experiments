package net.posesor.payments;

import lombok.Value;
import lombok.val;
import net.posesor.query.PaymentTitleView;
import net.posesor.query.PaymentTitlesQuery;
import org.axonframework.queryhandling.GenericQueryMessage;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@RestController
@RequestMapping(path = "/api/paymentTitle")
@Scope("request")
@SuppressWarnings("unused")
public class PaymentTitleController {

    private Supplier<String> userName = () -> {
        val authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    };

    private final QueryBus queryBus;

    @Autowired
    public PaymentTitleController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping(path = "/search/{hint}")
    public CompletableFuture<ResponseEntity> searchAccountName(@PathVariable String hint) {
        val query = new GenericQueryMessage<PaymentTitlesQuery, List<PaymentTitleView>>(
                new PaymentTitlesQuery(userName.get(), hint),
                ResponseTypes.multipleInstancesOf(PaymentTitleView.class)
        );
        return queryBus
                .query(query)
                .thenApply(it -> it
                        .getPayload()
                        .stream()
                        .map(PaymentTitleView::getName)
                        .map(SearchDtoModel::new)
                        .toArray()
                ).thenApply(ResponseEntity::ok);
    }

    @Value
    static class SearchDtoModel {
        private String name;
    }
}
