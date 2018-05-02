package net.posesor.payments;

import lombok.Value;
import lombok.val;
import net.posesor.SearchEngine;
import net.posesor.SessionToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/api/paymentTitle")
@Scope("request")
@SuppressWarnings("unused")
public class PaymentTitleController {
    private final Stream<String> content;

    @Autowired
    public PaymentTitleController(MongoOperations operations, SessionToken sessionToken) {
        this(Mapper.map(new PaymentOperations(operations, sessionToken.getUserName()).getAll())
                .flatMap(it -> Arrays.stream(it.getEntries()))
                .map(PaymentDto.PaymentEntry::getPaymentTitle));
    }

    public PaymentTitleController(Stream<String> content) {
        this.content = content;
    }

    @GetMapping(path = "/search/{hint}")
    public ResponseEntity<List<SearchDtoModel>> searchAccountName(@PathVariable String hint) {
        val result = SearchEngine
                .filterToSearchDtoModel(content, it -> it, hint)
                .map(SearchDtoModel::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Value
    static class SearchDtoModel {
        private String name;
    }
}
