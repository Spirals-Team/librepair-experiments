package net.posesor;

import lombok.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public final class Subject {

    @AggregateIdentifier
    private String subjectId;
    private String principalName;

    private final List<ChargeModel> unallocatedCharges = new ArrayList<>();
    private final List<ExpenseModel> unallocatedExpenses = new ArrayList<>();

    private final List<ReservedChargeModel> reservedCharges = new ArrayList<>();
    private final List<ReservedExpenseModel> reservedExpenses = new ArrayList<>();

    @CommandHandler
    public Subject(SubjectCreateCommand cmd) {
        apply(new SubjectCreatedEvent(cmd.getPrincipalName(), cmd.getSubjectId(), cmd.getName()));
    }

    @EventSourcingHandler
    public void on(SubjectCreatedEvent event) {
        subjectId = event.getSubjectId();
        principalName = event.getPrincipalName();
    }

    @CommandHandler
    public void handle(AddChargeCommand cmd) {
        apply(new SubjectChargeAddedEvent(cmd.getDocumentId(), cmd.getSettlementAccountId(), cmd.getPaymentDate(), cmd.getPaymentTitle(), cmd.getAmount()));
        apply(new UnallocatedChargeCreatedEvent(principalName, subjectId, cmd.getPaymentDate(), cmd.getPaymentTitle(), cmd.getAmount()));
    }

    @CommandHandler
    public void handle(RemoveChargeCommand cmd) {
        apply(new ChargeRemovedEvent(cmd.getDocumentId()));
        apply(new UnallocatedChargeRemovedEvent(principalName, subjectId, cmd.getPaymentDate(), cmd.getPaymentTitle(), cmd.getAmount()));
    }

    @EventSourcingHandler
    public void on(SubjectChargeAddedEvent event) {
        val model = new ChargeModel(
                new SubjectDocumentsReservedEvent.ChargeDocumentId(event.getDocumentId()),
                event.getSettlementAccountId(),
                event.getPaymentDate(),
                event.getPaymentTitle(),
                event.getAmount());
        unallocatedCharges.add(model);
    }

    @EventSourcingHandler
    public void on(ChargeRemovedEvent event) {
        unallocatedCharges
                .stream()
                .filter(it -> Objects.equals(it.documentId, event.documentId))
                .findFirst()
                .ifPresent(it -> unallocatedCharges.remove(it));
    }

    @CommandHandler
    public void handle(AddExpenseCommand cmd) {
        val event = new UnallocatedExpenseAddedEvent(
                this.subjectId,
                this.principalName,
                cmd.documentId,
                cmd.paymentDate,
                cmd.paymentTitle,
                cmd.amount);
        apply(event);
    }

    @EventSourcingHandler
    public void on(UnallocatedExpenseAddedEvent event) {
        val model = new ExpenseModel(
                new SubjectDocumentsReservedEvent.ExpenseDocumentId(event.getDocumentId()),
                event.getPaymentDate(),
                event.getPaymentTitle(),
                event.getAmount()
        );
        unallocatedExpenses.add(model);
    }

    @CommandHandler
    public void handle(SubjectReserveDocumentsCommand cmd) {
        // given criteria
        val periodFrom = cmd.getPeriodFrom();
        val periodTo = cmd.getPeriodTo();
        val paymentTitle = cmd.getPaymentTitle();

        // select documents covered by required context (paymentTitle and from-to periods)

        // TODO prevent expense documents to be selected twice for allocation
        // select for settlement only unallocatedExpenses compatible with given criteria
        val expensesToSettle = unallocatedExpenses.stream()
                .filter(it -> !YearMonth.from(it.getPaymentDate()).isBefore(periodFrom))
                .filter(it -> !YearMonth.from(it.getPaymentDate()).isAfter(periodTo))
                .filter(it -> Objects.equals(it.paymentTitle, paymentTitle))
                .map(ExpenseModel::getDocumentId)
                .toArray(SubjectDocumentsReservedEvent.ExpenseDocumentId[]::new);

        // list all matching charges
        val chargesForAllocation = unallocatedCharges.stream()
                .filter(it -> !YearMonth.from(it.getPaymentDate()).isBefore(periodFrom))
                .filter(it -> !YearMonth.from(it.getPaymentDate()).isAfter(periodTo))
                .filter(it -> Objects.equals(it.paymentTitle, paymentTitle))
                .collect(Collectors.toList());
        // and finally emit information about just made reservation
        val chargesIdsForAllocation = chargesForAllocation.stream()
                .map(ChargeModel::getDocumentId)
                .toArray(SubjectDocumentsReservedEvent.ChargeDocumentId[]::new);

        val event = new SubjectDocumentsReservedEvent(cmd.getCorrelationId(), this.subjectId, expensesToSettle, chargesIdsForAllocation);
        apply(event);
    }

    /**
     * Some documents are reserved for allocation process.
     *
     * @param evt
     */
    @EventSourcingHandler
    public void on(SubjectDocumentsReservedEvent evt) {

        // convert ids of charge documents to list of documents
        Arrays.stream(evt.getExpenseDocumentIds())
                .forEach(it -> {
                    val item = unallocatedExpenses.stream().filter(id -> Objects.equals(id.documentId, it)).findFirst();
                    // remove them from available documents
                    item.ifPresent(unallocatedExpenses::remove);
                    // add them to reserved documents
                    item.map(id -> new ReservedExpenseModel(evt.getCorrelationId(), id)).ifPresent(reservedExpenses::add);
                });

        // convert ids of charge documents to list of documents
        Arrays.stream(evt.getChargeDocumentsIds())
                .forEach(it -> {
                    val item = unallocatedCharges.stream().filter((id -> Objects.equals(id.documentId, it))).findFirst();
                    item.ifPresent(unallocatedCharges::remove);
                    item.map(id -> new ReservedChargeModel(evt.getCorrelationId(), id)).ifPresent(reservedCharges::add);
                });
    }

    @CommandHandler
    public void when(SubjectAllocateReservedDocumentsCommand cmd) {
        // select already reserved charges
        val reserved = reservedCharges.stream()
                .filter(it -> Objects.equals(it.correlationId, cmd.getCorrelationId()))
                .map(it -> it.originatingModel);
        reserved
                .map(it -> new UnallocatedChargeRemovedEvent(principalName, subjectId, it.getPaymentDate(), it.getPaymentTitle(), it.getAmount()))
                .forEach(AggregateLifecycle::apply);


        // TODO handle UnallocatedChargeRemovedEvent and remove from memory - e.g. from reservedCharges
    }

    @Value
    private static class ChargeModel {
        private SubjectDocumentsReservedEvent.ChargeDocumentId documentId;
        private String settlementAccountId;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
    }

    @Value
    private static class ReservedChargeModel {
        private String correlationId;
        private ChargeModel originatingModel;
    }

    @Value
    private static class ExpenseModel {
        private SubjectDocumentsReservedEvent.ExpenseDocumentId documentId;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
    }

    @Value
    private static class ReservedExpenseModel {
        private String correlationId;
        private ExpenseModel originatingModel;
    }


    /**
     * Add new ??
     */
    @Value
    static class AddChargeCommand {
        @TargetAggregateIdentifier
        private String subjectId;
        private String principalName;
        private String documentId;
        private String settlementAccountId;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
    }

    @Value
    static class RemoveChargeCommand {
        @TargetAggregateIdentifier
        private String subjectId;
        private String documentId;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
    }

    @Value
    private static final class ChargeRemovedEvent {
        private String documentId;
    }

    @Value
    static class AddExpenseCommand {
        @TargetAggregateIdentifier
        private String subjectId;
        private String principalName;
        private String documentId;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
    }

}
