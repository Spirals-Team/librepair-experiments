package net.posesor;

import com.google.common.collect.Iterables;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.val;
import net.posesor.app.events.AllocationsCompleted;
import net.posesor.domain.events.ChargeDocumentReservedEvent;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.StreamUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

/**
 * Allocation of common expenses.
 */
@Saga
@NoArgsConstructor
public final class AllocationCreateSaga {

    @Autowired
    private transient CommandGateway commandBus;

    /**
     * list of expense and charge documents included in the allocation of common expenses.
     */
    private List<String> expenseDocumentIds;
    private List<String> chargeDocumentsIds;

    private List<ExpenseModel> reservedExpenses;
    private List<ChargeModel> reservedCharges;

    private String principalName;
    private LocalDate operationDate;

    private String subjectId;

    @StartSaga
    @SagaEventHandler(associationProperty = "correlationId")
    public void on(AllocationAggregate.CreatedEvent e) {

        expenseDocumentIds = new ArrayList<>();
        chargeDocumentsIds = new ArrayList<>();

        reservedExpenses = new ArrayList<>();
        reservedCharges = new ArrayList<>();

        principalName = e.getPrincipalName();
        operationDate = e.getOperationDate();

        subjectId = e.getSubjectId();

        val cmd = new SubjectReserveDocumentsCommand(e.getSubjectId(), e.getCorrelationId(), e.getPaymentTitle(), e.getPeriodFrom(), e.getPeriodTo());
        commandBus.send(cmd, LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "correlationId")
    public void on(SubjectDocumentsReservedEvent e) {
        val correlationId = e.getCorrelationId();
        // TODO support case when incoming list is empty
        for (val documentId : e.getChargeDocumentsIds()) {
            // TODO reserve documents to avoid using same documents in different allocations.
            val cmd = new UnallocatedChargeDocument.ReserveDocumentCommand(documentId.getValue(), correlationId);
            chargeDocumentsIds.add(documentId.getValue());
            this.commandBus.send(cmd, LoggingCallback.INSTANCE);
        }

        for (val documentId : e.getExpenseDocumentIds()) {
            // TODO reserve documents to avoid using same documents in different allocations.
            expenseDocumentIds.add(documentId.getValue());
            val cmd = new ExpenseDocument.ReserveChargeDocumentCommand(documentId.getValue(), correlationId);
            this.commandBus.send(cmd, LoggingCallback.INSTANCE);
        }

        // let's assume document are already linked with the allocation.
        // now it's time to create correction documents.
        val cmd = new SubjectAllocateReservedDocumentsCommand(e.getSubjectId(), e.getCorrelationId());
        commandBus.send(cmd, LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "correlationId")
    public void on(ChargeDocumentReservedEvent e) {
        val principalName = e.getPrincipalName();
        String documentId = e.getDocumentId();
        val amount = e.getAmount();
        val customerId = e.getCustomerId();
        val customerName = e.getCustomerName();
        val subjectName = e.getSubjectName();
        val paymentTitle = e.getPaymentTitle();

        chargeDocumentsIds.remove(documentId);
        reservedCharges.add(new ChargeModel(principalName, documentId, customerId,  customerName, subjectId, subjectName, paymentTitle, amount));

        progressIfDocumentsAlreadyReserved();
    }

    @SagaEventHandler(associationProperty = "correlationId")
    public void on(ExpenseDocument.ExpenseDocumentReservedEvent e) {
        String documentId = e.getDocumentId();
        val amount = e.getAmount();

        expenseDocumentIds.remove(documentId);
        reservedExpenses.add(new ExpenseModel(documentId, amount));

        progressIfDocumentsAlreadyReserved();
    }

    /**
     * Method starts core operation of allocations. Needs to be invoked only if all related documents are already reserved
     * so to avoid using same documents twice in separated allocations.
     */
    private void progressIfDocumentsAlreadyReserved() {
        // do not try to allocate costs if not all document are ready for calculation.
        if (!chargeDocumentsIds.isEmpty()) return;
        if (!expenseDocumentIds.isEmpty()) return;

        val allocations = calculateCostsAllocation(reservedExpenses, reservedCharges);

        for (val item : allocations) {
            val newDocumentId = UUID.randomUUID().toString();
            val cmd = new AllocationDocument.CreateCommand (
                    item.origin.principalName,
                    newDocumentId,
                    item.origin.subjectId,
                    item.origin.subjectName,
                    item.origin.customerId,
                    item.origin.customerName,
                    operationDate, item.origin.paymentTitle, item.amount);
            commandBus.send(cmd);
        }

        // TODO wait when all documents will be stored and allocation options will be updated, and only send the notification below.
        commandBus.send(new AllocationsCompleted(principalName));
    }

    /***
     * Produces list of allocation for provided set of expenses and charges.
     * @param expenses list of allocated costs.
     * @param charges list of charges in advance of predicted costs.
     * @return List of calculated corrections of documents.
     * @throws IllegalStateException if can't allocate costs.
     */
    public static AllocationResult[] calculateCostsAllocation(Iterable<ExpenseModel> expenses, Iterable<ChargeModel> charges) {
        // in default allocation case 'no charges' is invalid.
        // // in that allocation we need charges, because they play role to define percentage
        // // split of costs between charges' owners.
        if (Iterables.isEmpty(charges)) throw new IllegalStateException();

        // calculate total cost
        val expensesTotal = StreamUtils.createStreamFromIterator(expenses.iterator())
                .map(ExpenseModel::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // calculate total allocatedCharges
        val chargesTotal = StreamUtils.createStreamFromIterator(charges.iterator())
                .map(ChargeModel::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // calculate amount which need to be spread among charges' owners.
        val differenceToBePaid = chargesTotal.subtract(expensesTotal);
        // TODO analyse scenario where differenceToBePaid is not paid fully because of rounding issue.
        val allocationSubtotals = new ArrayList<AllocationResult>();
        for (val charge : charges) {
            val subtotal = charge.amount
                    .divide(chargesTotal, 4, RoundingMode.CEILING)
                    .multiply(differenceToBePaid)
                    .setScale(2, RoundingMode.CEILING)
                    .negate();

            val chargeWithSubtotal = new AllocationResult(charge, subtotal);
            allocationSubtotals.add(chargeWithSubtotal);
        }

        return allocationSubtotals.toArray(new AllocationResult[0]);
    }

    @Value
    private static class ChargeModel {
        private String principalName;
        private String documentId;
        private String customerId;
        private String customerName;
        private String subjectId;
        private String subjectName;
        private String paymentTitle;
        private BigDecimal amount;
    }

    @Value
    private static class ExpenseModel {
        private String documentId;
        private BigDecimal amount;
    }

    @Value
    private static class AllocationResult {
        private ChargeModel origin;
        private BigDecimal amount;
    }

}
