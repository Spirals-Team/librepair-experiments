package net.posesor.domain.events;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public final class ExpenseDocumentUpdatedEvent {
    private String expenseDocumentId;
    private String subjectId;
    private String subjectName;
    private String customerName;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
    private String description;
}
