package net.posesor;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.posesor.domain.events.AllocationDocumentCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Represents result of allocation created per a customer
 */
@Aggregate
@NoArgsConstructor
@Slf4j
public class AllocationDocument {

    @AggregateIdentifier
    private String documentId;

    @CommandHandler
    public AllocationDocument(CreateCommand cmd) {
        val item = AllocationDocumentCreatedEvent.builder()
                .documentId(cmd.documentId)
                .principalName(cmd.principalName)
                .subjectId(cmd.subjectId)
                .subjectName(cmd.subjectName)
                .customerId(cmd.customerId)
                .customerName(cmd.customerName)
                .paymentDate(cmd.paymentDate)
                .paymentTitle(cmd.paymentTitle)
                .amount(cmd.amount)
                .build();

        apply(item);
    }

    @EventSourcingHandler
    public void on(AllocationDocumentCreatedEvent evt) {
        this.documentId = evt.getDocumentId();
    }

    /***
     * Creates a new new instance of {@link AllocationDocument}
     *
     * Create command doesn't contain IDs of customer or payment because - by design - to create paper document
     * is enough to know some words, not ids.
     */
    @Value
    public static class CreateCommand {
        private String principalName;
        private String documentId;
        private String subjectId;
        private String subjectName;
        private String customerId;
        private String customerName;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
    }

}
