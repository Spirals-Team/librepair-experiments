package net.posesor.subjects;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.posesor.query.SubjectSuggestionQuery;
import net.posesor.query.SubjectSuggestionView;
import org.axonframework.queryhandling.GenericQueryMessage;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/subjects")
@Scope("session")
@Slf4j
public class SubjectsEndpoint {

    private final QueryBus queryBus;

    SubjectsEndpoint(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    private Supplier<String> userName = () -> {
        val authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    };

    @GetMapping("/suggest/{hint}")
    @SneakyThrows
    public List<String> suggest(@PathVariable String hint) {

        val queryMessage = new GenericQueryMessage<SubjectSuggestionQuery, List<SubjectSuggestionView>>(
                new SubjectSuggestionQuery(userName.get(), hint),
                ResponseTypes.multipleInstancesOf(SubjectSuggestionView.class));

        // TODO change to async
        val result = queryBus.query(queryMessage).get().getPayload();
        // return Collections.singletonList("Nowa wspólnota: " + hint);
        return result
                .stream()
                .map(SubjectSuggestionView::getSubjectName)
                .collect(Collectors.toList());
    }
}
