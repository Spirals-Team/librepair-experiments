package net.posesor.domain.events;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Charge has been removed from a Receivable Account.
 *
 * TODO Rename to UnsettledChargeRemoved
 */
@Value
public class ChargeRemovedEvent {
    private String settlementAccountId;
    private String principalName;
    private String documentId;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
