package net.posesor;

import lombok.Value;
import lombok.val;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/***
 * CostsAllocation allows to calculate Charges correction based on the difference between expenses and
 * receivables.
 *
 * Scenario 1 Allocation of costs
 * Given total cost of Refuse 300 PLN
 * And you collected fees for Refuse from HoA members 100 PLN
 * When allocate
 * Then 200 PLN should be returned to HoA members in proper amounts.
 *
 * Scenario 2 Locking source documents
 * Given source documents
 * When created
 * Make all documents non editable
 *
 */
@Aggregate
public final class AllocationAggregate {

    @AggregateIdentifier
    private String allocationId;

    @CommandHandler
    public AllocationAggregate(CreateCommand cmd) {
        val event = new CreatedEvent(
                cmd.getPrincipalName(),
                cmd.getAllocationId(), cmd.getSubjectId(), cmd.getPaymentTitle(),
                cmd.getPeriodFrom(), cmd.getPeriodTo(), cmd.getOperationDate());

        apply(event);
    }

    @EventSourcingHandler
    public void on(CreatedEvent event) {
        this.allocationId = event.correlationId;
    }


    @Value
    public static class CreateCommand {
        private String principalName;
        private String allocationId;
        private String subjectId;
        private String paymentTitle;
        private YearMonth periodFrom;
        private YearMonth periodTo;
        private LocalDate operationDate;
    }

    /***
     * Emitted when new CostsAllocation has been started.
     *
     * Content of the event defines scope of the CostsAllocation.
     */
    @Value
    public static class CreatedEvent {
        private String principalName;
        private String correlationId;
        private String subjectId;
        private String paymentTitle;
        private YearMonth periodFrom;
        private YearMonth periodTo;
        private LocalDate operationDate;
    }
}
