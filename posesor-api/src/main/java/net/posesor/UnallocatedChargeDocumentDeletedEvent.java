package net.posesor;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class UnallocatedChargeDocumentDeletedEvent {
    private String documentId;
    private String principalName;
    private String subjectId;
    private String accountReceivableId;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
