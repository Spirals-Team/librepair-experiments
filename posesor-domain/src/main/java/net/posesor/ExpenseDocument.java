package net.posesor;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.val;
import net.posesor.domain.events.ExpenseDocumentCreatedEvent;
import net.posesor.domain.events.ExpenseDocumentDeletedEvent;
import net.posesor.domain.events.ExpenseDocumentUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor // constructor needed for reconstruction
public class ExpenseDocument {

    @AggregateIdentifier
    private String expenseDocumentId;
    private String principalName;
    private BigDecimal amount;

    public ExpenseDocument(ExpensePayload payload) {
        val event = new ExpenseDocumentCreatedEvent(
                payload.documentId,
                payload.principalName,
                payload.subjectId, payload.subjectName, payload.customerName,
                payload.paymentDate, payload.paymentTitle, payload.amount,
                payload.description);
        apply(event);
    }

    @EventSourcingHandler
    public void on(ExpenseDocumentCreatedEvent e) {
        this.expenseDocumentId = e.getDocumentId();
        this.principalName = e.getPrincipalName();
        this.amount = e.getAmount();
    }

    @CommandHandler
    public void handle(ExpenseDocumentDeleteCommand cmd) {
        // need to avoid hacking - trying to delete ExpenseDocument owned by other principal.
        if (!Objects.equals(principalName, cmd.getPrincipalName())) throw new IllegalArgumentException();

        apply(new ExpenseDocumentDeletedEvent(expenseDocumentId, principalName));
    }

    @EventSourcingHandler
    public void on(ExpenseDocumentDeletedEvent event) {
        // Currently Aggregate don't ned to change state because of event - will be coded later on with proper test.
    }

    @EventSourcingHandler
    public void on(ExpenseDocumentUpdatedEvent event) {
        // Currently Aggregate don't ned to change state because of event - will be coded later on with proper test.
    }

    /**
     * Document is marked as reserved. Will be locked for changes as long as reservation will be active.
     *
     * @param cmd Command
     */
    @CommandHandler
    public void handler(ReserveChargeDocumentCommand cmd) {
        // TODO throw exception if already reserved
        val event = new ExpenseDocumentReservedEvent(expenseDocumentId, cmd.getCorrelationId(), amount);
        apply(event);
    }

    public void update(String subjectId,
                       String subjectName, String customerName,
                       LocalDate paymentPeriod, String paymentTitle, BigDecimal amount, String description) {
        apply(new ExpenseDocumentUpdatedEvent(expenseDocumentId,
                subjectId, subjectName, customerName,
                paymentPeriod, paymentTitle, amount, description));
    }

    /**
     * Container used to initialize ExpenseDocument ctor with multiple value.
     * <p>
     * aaa
     */
    @Value
    public static final class ExpensePayload {
        private String documentId;
        private String principalName;
        private String subjectId;
        private String subjectName;
        private String customerName;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
        private String description;
    }

    @Value
    public static final class CreateCommand {
        private String documentId;
        private String principalName;
        private String subjectName;
        private String customerName;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
        private String description;
    }

    @Value
    public static final class ExpenseDocumentDeleteCommand {
        @TargetAggregateIdentifier
        private String expenseDocumentId;
        private String principalName;
    }

    @Value
    public static final class UpdateCommand {
        @TargetAggregateIdentifier
        private String documentId;
        private String principalName;
        private String subjectName;
        private String customerName;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
        private String description;
    }

    @Value
    public static final class ReserveChargeDocumentCommand {
        @TargetAggregateIdentifier
        private String documentId;
        private String correlationId;
    }

    @Value
    public static final class ExpenseDocumentReservedEvent {
        private String documentId;
        private String correlationId;
        private BigDecimal amount;
    }
}
