package net.posesor.unallocated.documents;

import lombok.val;
import net.posesor.domain.events.AccountsReceivableChargedEvent;
import net.posesor.domain.events.ChargeRemovedEvent;
import net.posesor.UnallocatedExpenseAddedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Component
public final class EventListener {

    private final QueryRepository repository;

    public EventListener(QueryRepository repository) {
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

        val entry = new UnallocatedDocument();
        entry.setId(UUID.randomUUID().toString());
        entry.setPrincipalName(principalName);
        entry.setSubjectId(settlementAccountId);
        entry.setPaymentTitle(paymentTitle);
        entry.setYear(year);
        entry.setMonth(month);

        entry.setChargesTotal(event.getAmount());
        entry.setExpensesTotal(BigDecimal.ZERO);
        repository.save(entry);
    }

    @EventHandler
    public void on(ChargeRemovedEvent event) {
        val probe = new UnallocatedDocument();
        probe.setYear(event.getPaymentDate().getYear());
        probe.setMonth(event.getPaymentDate().getMonthValue());
        probe.setPaymentTitle(event.getPaymentTitle());
        probe.setSubjectId(event.getSettlementAccountId());
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

    @EventHandler
    public void on(UnallocatedExpenseAddedEvent event) {
        val subjectId = event.getSubjectId();
        val principalName = event.getPrincipalName();
        val paymentTitle = event.getPaymentTitle();
        val period = YearMonth.from(event.getPaymentDate());
        val year = period.getYear();
        val month = period.getMonthValue();

        val entry = new UnallocatedDocument();
        entry.setId(UUID.randomUUID().toString());
        entry.setPrincipalName(principalName);
        entry.setSubjectId(subjectId);
        entry.setPaymentTitle(paymentTitle);
        entry.setYear(year);
        entry.setMonth(month);

        entry.setChargesTotal(BigDecimal.ZERO);
        entry.setExpensesTotal(event.getAmount());
        repository.save(entry);
    }

}
