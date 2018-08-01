package net.posesor;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * New expense document is defined for a Subject so it needs to be allocated in the future.
 */
@Value
public final class UnallocatedExpenseAddedEvent {
    @TargetAggregateIdentifier
    private String subjectId;
    private String principalName;
    private String documentId;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
