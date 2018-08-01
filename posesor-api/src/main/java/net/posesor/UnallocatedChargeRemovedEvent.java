package net.posesor;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class UnallocatedChargeRemovedEvent {
    private String principalName;
    private String subjectId;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
