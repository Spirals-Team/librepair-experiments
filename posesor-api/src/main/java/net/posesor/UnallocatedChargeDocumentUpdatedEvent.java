package net.posesor;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class UnallocatedChargeDocumentUpdatedEvent {
    private String documentId;
    private String subjectId;
    private String subjectName;
    private String customerId;
    private String customerName;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
