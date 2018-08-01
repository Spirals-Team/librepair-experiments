package net.posesor.unallocated.documents;

import lombok.val;
import net.posesor.query.UnallocatedDocumentsQuery;
import net.posesor.query.UnallocatedDocumentsView;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service("UnallocatedDocumentsQueryProcessor")
public class QueryProcessor {

    @Autowired
    private QueryRepository queryRepository;

    @QueryHandler
    public List<UnallocatedDocumentsView> on(UnallocatedDocumentsQuery query) {
        val example = new UnallocatedDocument();
        example.setPrincipalName(query.getPrincipalName());
        val itemsList = queryRepository.findAll(Example.of(example));
        return itemsList.stream()
                .map(it -> new UnallocatedDocumentsView(
                        it.getSubjectId(),
                        it.getPaymentTitle(),
                        YearMonth.of(it.getYear(), it.getMonth()),
                        it.getChargesTotal(),
                        it.getExpensesTotal()))
                .collect(Collectors.toList());
    }
}