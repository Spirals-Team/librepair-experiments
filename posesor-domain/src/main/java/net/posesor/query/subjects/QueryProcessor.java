package net.posesor.query.subjects;

import lombok.val;
import net.posesor.query.SubjectQuery;
import net.posesor.query.SubjectSuggestionQuery;
import net.posesor.query.SubjectSuggestionView;
import net.posesor.query.SubjectView;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("SubjectQueryProcessor")
public class QueryProcessor {

    private final SubjectRepository repository;

    QueryProcessor(SubjectRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    public List<SubjectView> on(SubjectQuery query) {
        val probe = new SubjectEntry();
        probe.setPrincipalName(query.getPrincipalName());

        val result = new ArrayList<SubjectView>();

        for (val subjectId : query.getSubjectIds()) {
            probe.setSubjectId(subjectId);
            val template = Example.of(probe);
            val subject = repository.findOne(template);
            if (subject == null) continue;

            result.add(new SubjectView(subjectId, subject.getName()));
        }

        return result;
    }

    @QueryHandler
    public List<SubjectSuggestionView> on(SubjectSuggestionQuery query) {
        val probe = new SubjectEntry();
        probe.setPrincipalName(query.getPrincipalName());

        val lowerHint = query.getHint().toLowerCase();
        val candidates = repository.findAll(Example.of(probe));
        val filtered = candidates
                .stream()
                .filter(it -> it.getName().toLowerCase().contains(lowerHint))
                .map(it -> new SubjectSuggestionView(it.getName()))
                .collect(Collectors.toList());
        Collections.sort(filtered, (v1, v2) -> v1.getSubjectName().compareTo(v1.getSubjectName()));
        return filtered;
    }
}
