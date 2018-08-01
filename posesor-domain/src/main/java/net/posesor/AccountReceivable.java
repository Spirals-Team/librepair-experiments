package net.posesor;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.posesor.domain.events.AccountsReceivableChargedEvent;
import net.posesor.domain.events.AccountsReceivableCreatedEvent;
import net.posesor.domain.events.ChargeRemovedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Represents domain model for an receivable account with all non-settled charges.
 * In other words, it contains all not-TBD
 */
@Slf4j
@Aggregate
@NoArgsConstructor // constructor needed for reconstruction
public class AccountReceivable {

    @AggregateIdentifier
    private String accountId;

    private String principalName;

    // list of financial operations related to the Settlement Account. It contains payments and
    private List<Financials> financials = new ArrayList<>();

    @CommandHandler
    public AccountReceivable(CreateCommand command) {
        apply(
                new AccountsReceivableCreatedEvent(
                        command.getPrincipalName(),
                        command.getAccountId(),
                        command.getCustomerName(),
                        command.getSubjectId()));
    }

    @EventSourcingHandler
    public void on(AccountsReceivableCreatedEvent event) {
        accountId = event.getAccountId();
        principalName = event.getPrincipalName();
    }

    @CommandHandler
    public void handle(AddChargeCommand cmd) {
        apply(new AccountsReceivableChargedEvent(accountId, principalName, cmd.getDocumentId(), cmd.getPaymentDate(), cmd.getPaymentTitle(), cmd.getAmount()));
    }

    @CommandHandler
    public void handle(RemoveChargeCommand cmd) {

        val entry = financials.stream()
                .filter(it -> Objects.equals(it.documentId, cmd.documentId))
                .findFirst();

        if (!entry.isPresent()) return;
        val value = entry.get();

        apply(new ChargeRemovedEvent(accountId, principalName, cmd.getDocumentId(), value.getPaymentDate(),
                value.getPaymentTitle(), value.getChargeAmount()));
    }

    @EventSourcingHandler
    public void on(AccountsReceivableChargedEvent event) {
        val newEntry = new Financials(event.getDocumentId(), event.getPaymentDate(),
                event.getPaymentTitle(), event.getAmount());
        financials.add(newEntry);
    }

    @EventSourcingHandler
    public void on(ChargeRemovedEvent event) {
        financials.stream()
                .filter(it -> Objects.equals(it.documentId, event.getDocumentId()))
                .findFirst()
                .ifPresent(financials::remove);
    }


    /***
     * Simplified model to keep info about a receivable account.
     */
    private static @Value class Financials {
        private String documentId;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal chargeAmount;
    }

    public static @Value class CreateCommand {
        private String accountId;
        private String customerName;
        private String principalName;
        private String subjectId;
    }

    public static @Value class AddChargeCommand {
        @TargetAggregateIdentifier
        private String settlementAccountId;
        private String documentId;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
    }

    public static @Value class RemoveChargeCommand {
        @TargetAggregateIdentifier
        private String settlementAccountId;
        private String documentId;
    }
}