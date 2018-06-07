package net.posesor.allocations;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.var;
import lombok.extern.java.Log;
import lombok.val;
import net.posesor.query.SubjectQuery;
import net.posesor.query.SubjectView;
import net.posesor.query.UnallocatedPeriodsQuery;
import net.posesor.query.UnallocatedPeriodsView;
import org.axonframework.queryhandling.GenericQueryMessage;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;

import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * The Obtainer allows do coordinate complex operation of retreving and combining partial data
 * to build list of available allocations.
 * <p>
 * Current implementation requires multiple asynchronous sources to construct list of available allocations per
 * requested principal. To simplify it, {@link AvailableAllocationsObtainer} allows to retrieve requested data.
 */
@Log
public final class AvailableAllocationsObtainer {

    private final QueryBus queryBus;
    private final String principalName;

    private final CompletableFuture<List<PeriodInfo>> onPeriods = new CompletableFuture<>();
    private final CompletableFuture<SubjectContext> onSubjects = new CompletableFuture<>();
    private final CompletableFuture<AvailableAllocationsDto> onResult = new CompletableFuture<>();

    private AvailableAllocationsObtainer(QueryBus queryBus, String principalName) {
        // initialize local variables required to start procesing
        this.queryBus = queryBus;
        this.principalName = principalName;

        onPeriods.whenComplete(this::onPeriods);
        onSubjects.whenComplete(this::onSubjects);
    }

    /**
     * Starts processing.
     *
     * Post-construct method required to start whole processing.
     */
    private void init() {
        val query = new GenericQueryMessage<UnallocatedPeriodsQuery, List<UnallocatedPeriodsView>>(
                new UnallocatedPeriodsQuery(principalName),
                ResponseTypes.multipleInstancesOf(UnallocatedPeriodsView.class));

        queryBus
                .query(query)
                .whenComplete((v, ex) -> {
                    val items = v.getPayload().stream()
                            .map(it -> PeriodInfo.builder()
                                    .subjectId(it.getSubjectId())
                                    .subjectName("nie zdefiniowany")
                                    .paymentTitle(it.getPaymentTitle())
                                    .from(it.getPeriodFrom())
                                    .till(it.getPeriodTo())
                                    .build())
                            .collect(Collectors.toList());
                    onPeriods.complete(items);
                });
    }

    private void onPeriods(List<PeriodInfo> periods, Throwable ex) {

        // TODO handle exceptions
        if (ex != null) log.log(Level.SEVERE, "TODO", ex);

        val query = new GenericQueryMessage<SubjectQuery, List<SubjectView>>(
                new SubjectQuery(principalName, periods.stream().map(it -> it.subjectId).collect(Collectors.toList())),
                ResponseTypes.multipleInstancesOf(SubjectView.class));

        queryBus
                .query(query)
                .whenComplete((v, ex_) -> onSubjects.complete(new SubjectContext(periods, v.getPayload())));
    }

    private void onSubjects(SubjectContext context, Throwable reason) {
        // TODO handle exceptions
        if (reason != null) log.log(Level.SEVERE, "TODO", reason);

        val periods = context.periods;
        val subjects = context.subjects;
        var elements = periods.stream()
                .map(it -> {
                    val subjectName = subjects.stream()
                            .findFirst().map(SubjectView::getSubjectName)
                            .orElse(it.subjectName);

                    return new AvailableAllocationsDto.SubjectDto(
                            subjectName,
                            it.paymentTitle,
                            new AvailableAllocationsDto.PeriodDto(it.from.getYear(), it.from.getMonthValue()),
                            new AvailableAllocationsDto.PeriodDto(it.till.getYear(), it.till.getMonthValue())
                    );
                })
                .toArray(AvailableAllocationsDto.SubjectDto[]::new);

        onResult.complete(new AvailableAllocationsDto(elements));
    }

    /**
     * Equivalent of {@link AllocationDocumentsDto} used in internal flow to build
     * full data model.
     */
    @Builder
    private static class PeriodInfo {
        private final String subjectId;
        private final String subjectName;
        private final String paymentTitle;
        private final YearMonth from;
        private final YearMonth till;
    }

    /**
     * Holds data items between invocations.
     */
    @Value
    private static class SubjectContext {
        // list of periods which needs to be processed to final result
        private List<PeriodInfo> periods;
        // list of subjects related to the periods.
        private List<SubjectView> subjects;
    }

    /**
     * Static factory method to obtain list of available allocations.
     * @param queryBus as query bus used to obtain data.
     * @param principalName defines the data owner - only its allocations will be included.
     * @return
     */
    public static CompletableFuture<AvailableAllocationsDto> of(QueryBus queryBus, String principalName) {
        val obtainer = new AvailableAllocationsObtainer(queryBus, principalName);
        obtainer.init();
        return obtainer.onResult;
    }

}
