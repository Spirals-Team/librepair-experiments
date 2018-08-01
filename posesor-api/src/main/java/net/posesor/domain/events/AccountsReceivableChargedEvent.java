package net.posesor.domain.events;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.LocalDate;

/***
 * Charge is already applied on a SettlementAccount.
 */
@Value
public class AccountsReceivableChargedEvent {
    @TargetAggregateIdentifier
    private String settlementAccountId;
    private String principalName;
    private String documentId;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
