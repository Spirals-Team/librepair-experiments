package net.posesor.accountcharges;

import lombok.val;
import net.posesor.AccountsReceivableChargedEvent;
import net.posesor.ChargeRemovedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;

@Component
public final class UnsettledAccountFinancialEventListener {

    private final UnsettledAccountFinancialRepository repository;

    public UnsettledAccountFinancialEventListener(UnsettledAccountFinancialRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(AccountsReceivableChargedEvent event) {
        val settlementAccountId = event.getSettlementAccountId();
        val principalName = event.getPrincipalName();
        val paymentTitle = event.getPaymentTitle();
        val period = YearMonth.from(event.getPaymentDate());
        val year = period.getYear();
        val month = period.getMonthValue();

        val entry = new UnsettledAccountFinancialEntry();
        entry.setId(UUID.randomUUID().toString());
        entry.setPrincipalName(principalName);
        entry.setSettlementAccountId(settlementAccountId);
        entry.setPaymentTitle(paymentTitle);
        entry.setYear(year);
        entry.setMonth(month);

        entry.setChargesTotal(Optional.ofNullable(entry.getChargesTotal()).orElse(BigDecimal.ZERO).add(event.getAmount()));
        entry.setExpensesTotal(BigDecimal.ZERO);
        repository.save(entry);
    }

    @EventHandler
    public void on(ChargeRemovedEvent event) {
        val probe = new UnsettledAccountFinancialEntry();
        probe.setYear(event.getPaymentDate().getYear());
        probe.setMonth(event.getPaymentDate().getMonthValue());
        probe.setPaymentTitle(event.getPaymentTitle());
        probe.setSettlementAccountId(event.getSettlementAccountId());
        probe.setPrincipalName(event.getPrincipalName());
        val example = Example.of(probe);

        val item = repository.findOne(example);
        item.setChargesTotal(item.getChargesTotal().subtract(event.getAmount()));
        if (item.getChargesTotal().equals(BigDecimal.ZERO) && item.getExpensesTotal().equals(BigDecimal.ZERO)) {
            repository.delete(item);
        } else {
            repository.save(item);
        }
    }

}
