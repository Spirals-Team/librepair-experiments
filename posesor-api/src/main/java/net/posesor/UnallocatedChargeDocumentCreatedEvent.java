package net.posesor;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class UnallocatedChargeDocumentCreatedEvent {
    private String documentId;
    private String principalName;
    private String subjectId;
    private String subjectName;
    private String accountReceivableId;
    private String customerName;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
