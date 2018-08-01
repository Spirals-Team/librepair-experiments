package net.posesor.domain.events;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class AllocationDocumentCreatedEvent {
    private String documentId;
    private String principalName;
    private String subjectId;
    private String subjectName;
    private String customerId;
    private String customerName;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
