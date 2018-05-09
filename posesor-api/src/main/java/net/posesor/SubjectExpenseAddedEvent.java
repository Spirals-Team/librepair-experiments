package net.posesor;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public final class SubjectExpenseAddedEvent {
    private String documentId;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
