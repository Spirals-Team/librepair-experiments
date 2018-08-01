package net.posesor;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public final class SubjectChargeAddedEvent {
    private String documentId;
    private String settlementAccountId;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
