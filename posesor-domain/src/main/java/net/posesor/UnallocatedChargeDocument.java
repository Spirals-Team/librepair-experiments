package net.posesor;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.val;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Represents a charge which needs to be allocated against common xosts in the future.
 */
@Aggregate
@NoArgsConstructor
public class UnallocatedChargeDocument {

    @AggregateIdentifier
    private String documentId;
    private String principalName;
    private Status status;
    private String subjectId;
    private String subjectName;
    private String settlementAccountId;
    private String customerId;
    private String customerName;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;

    /***
     * Creates a new instance of ChargeDocument.
     * <p>
     * ChargeDocument initiates charging operation against an user settlement account.
     * Creating a document doest't guarantee successfull operation - it only start operation.
     *
     * @param documentId    unique id of the document.
     * @param principalName owner name in terms of multitenant application
     * @param content       content of the document
     */
    public UnallocatedChargeDocument(String documentId, String principalName, Snapshot content) {

        apply(new UnallocatedChargeDocumentCreatedEvent(documentId, principalName,
                content.subjectId, content.subjectName, content.accountReceivableId, content.customerName,
                content.paymentDate, content.paymentTitle, content.amount));
    }

    @CommandHandler
    public void on(UnallocatedChargeDocumentDeleteCommand cmd) {
        apply(new UnallocatedChargeDocumentDeletedEvent(documentId, principalName, subjectId, settlementAccountId, paymentDate, paymentTitle, amount));
    }

    @EventSourcingHandler
    public void on(UnallocatedChargeDocumentCreatedEvent event, MetaData metadata) {
        // store document data
        documentId = event.getDocumentId();
        principalName = event.getPrincipalName();
        subjectId = event.getSubjectId();
        subjectName = event.getSubjectName();
        settlementAccountId = event.getCustomerId();
        customerId = event.getCustomerId();
        customerName = event.getCustomerName();
        paymentDate = event.getPaymentDate();
        paymentTitle = event.getPaymentTitle();
        amount = event.getAmount();

        // set status to wait for charge confirmation
        status = Status.CREATING;
    }

    @CommandHandler
    public void on(BookDocumentCommand cmd) {
        apply(new DocumentBookedEvent(cmd.getDocumentId()));
    }

    @EventSourcingHandler
    public void on(DocumentBookedEvent event) {
        status = Status.CREATED;
    }

    @CommandHandler
    public void on(ReserveDocumentCommand e) {
        val event = new ChargeDocumentReservedEvent(principalName, e.getDocumentId(), e.correlationId, customerId, customerName, subjectName, paymentTitle, this.amount);
        apply(event);
    }

    public void update(String subjectId, String subjectName, String settlementAccountId, String settlementAccountName, LocalDate paymentDate, String paymentTitle, BigDecimal amount) {
        apply(new UnallocatedChargeDocumentUpdatedEvent(documentId, subjectId, subjectName, settlementAccountId, settlementAccountName, paymentDate, paymentTitle, amount));
    }

    @EventSourcingHandler
    public void on(UnallocatedChargeDocumentUpdatedEvent event, @MetaDataValue("principalName") String principalName, MetaData metadata) {

        val last = new Snapshot(subjectId, subjectName, settlementAccountId, customerName, paymentDate, paymentTitle, amount);

        subjectId = event.getSubjectId();
        subjectName = event.getSubjectName();
        settlementAccountId = event.getCustomerId();
        customerName = event.getCustomerName();
        paymentDate = event.getPaymentDate();
        paymentTitle = event.getPaymentTitle();
        amount = event.getAmount();

        val current = new Snapshot(subjectId, subjectName, settlementAccountId, customerName, paymentDate, paymentTitle, amount);

        apply(new ContentChanged(documentId, principalName, last, current));
    }

    private enum Status {
        CREATING, // creating a new charge based on the document is in progress
        CREATED,  // initial charge is already applied in the system, document is ready for updates
        UPDATING, // removing old value and applying new value is in progress
    }

    @Value
    public static final class ContentChanged {
        private String documentId;
        private String principalName;
        private Snapshot last;
        private Snapshot current;
    }

    @Value
    public static final class Snapshot {
        private String subjectId;
        private String subjectName;
        private String accountReceivableId;
        private String customerName;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
    }

    @Value
    public static final class BookDocumentCommand {
        @TargetAggregateIdentifier
        private String documentId;
    }

    @Value
    public static final class ReserveDocumentCommand {
        @TargetAggregateIdentifier
        private String documentId;
        private String correlationId;
    }


    /***
     * Creates a new ChargeDocument.
     *
     * Create command doesn't contain IDs of customer or payment because - by design - to create paper document
     * is enough to know some words, not ids.
     */
    @Value
    public static class CreateCommand {
        private String principalName;
        private String documentId;
        private String customerName;
        private String subjectName;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
    }

    /**
     * Document is already in the system and has impact on finances.
     */
    @Value
    public static final class DocumentBookedEvent {
        private String documentId;
    }

    @Value
    public static class UpdateCommand {
        @TargetAggregateIdentifier
        private String documentId;
        private String customerName;
        private String subjectName;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
    }
}
