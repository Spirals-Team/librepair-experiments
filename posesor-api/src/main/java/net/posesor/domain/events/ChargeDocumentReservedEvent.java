package net.posesor.domain.events;

import lombok.Value;

import java.math.BigDecimal;

/**
 * Document is (or will be) used in some financial operations and can't be changed.
 */
@Value
public final class ChargeDocumentReservedEvent {
    private String principalName;
    private String documentId;
    private String correlationId;
    private String customerId;
    private String customerName;
    private String subjectName;
    private String paymentTitle;
    private BigDecimal amount;
}

