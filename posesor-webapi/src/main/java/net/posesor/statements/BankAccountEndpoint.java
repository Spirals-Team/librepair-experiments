package net.posesor.statements;

import lombok.Value;
import lombok.val;
import net.posesor.SearchEngine;
import net.posesor.SessionToken;
import net.posesor.payments.PaymentOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/api/banking/statements")
@SuppressWarnings("unused")
@Scope("request")
public class BankAccountEndpoint {

    private final Stream<SearchDtoModel> source;

    @Autowired
    public BankAccountEndpoint(MongoOperations operations, SessionToken sessionToken) {
        this(new PaymentOperations(operations, sessionToken.getUserName()).getAll().stream().map(it -> new SearchDtoModel(it.getAccountName(), it.getSubjectName())));
    }

    public BankAccountEndpoint(Stream<SearchDtoModel> source) {
        this.source = source;
    }

    @GetMapping(path = "/search/{hint}")
    public ResponseEntity<List<SearchDtoModel>> searchAccountName(@PathVariable String hint) {
        val result = SearchEngine
                .filterToSearchDtoModel(source, SearchDtoModel::getName, hint)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Value
    public static class SearchDtoModel {
        private String name;
        private String subjectName;
    }
}
