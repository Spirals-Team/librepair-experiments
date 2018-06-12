package net.posesor.unallocated.periods;

import lombok.Value;
import lombok.val;
import net.posesor.query.UnallocatedPeriodsQuery;
import net.posesor.query.UnallocatedPeriodsView;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service("UnallocatedPeriodsQueryProcessor")
public class QueryProcessor {

    private UnallocatedPeriodRepository queryRepository;

    public QueryProcessor(UnallocatedPeriodRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @QueryHandler
    public List<UnallocatedPeriodsView> on(UnallocatedPeriodsQuery query) {
        val example = new UnallocatedPeriod();
        example.setPrincipalName(query.getPrincipalName());

        @Value
        class PeriodKey {
            private String subjectId;
            private String paymentTitle;
        }

        @Value
        class MinMax {
            private YearMonth periodFrom;
            private YearMonth periodTo;
        }

        // when finishing stream with a collector, we needs - according to Collector rules - some entity
        // called 'identity' which is neutral for collecting data.
        val identity = new MinMax(YearMonth.of(1900, 1), YearMonth.of(1900, 1));

        // aside identity, we need logic which is aware of identity and can merge data according to Collector rules
        val merge = (BinaryOperator<MinMax>)
                (v1, v2) -> v1 == identity
                        ? v2
                        : new MinMax(Comparer.lower(v1.periodFrom, v2.periodFrom), Comparer.bigger(v1.periodTo, v2.periodTo));


        val reducer = Collectors.reducing(
                identity,
                (UnallocatedPeriod k) -> {
                    val period = YearMonth.of(k.getYear(), k.getMonth());
                    return new MinMax(period, period);
                },
                merge);


        val itemsList = queryRepository.findAll(Example.of(example));
        return itemsList
                .stream()
                .collect(
                        groupingBy(
                                it -> new PeriodKey(it.getSubjectId(), it.getPaymentTitle()), reducer))
                .entrySet()
                .stream()
                .map(it -> UnallocatedPeriodsView.builder()
                        .subjectId(it.getKey().getSubjectId())
                        .paymentTitle(it.getKey().getPaymentTitle())
                        .periodFrom(it.getValue().getPeriodFrom())
                        .periodTo(it.getValue().getPeriodTo())
                        .build())
                .collect(Collectors.toList());
    }

}

