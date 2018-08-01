package net.posesor.unallocated.periods;

import lombok.experimental.UtilityClass;
import lombok.val;
import net.posesor.app.events.NotifySettlementsChangedCommand;
import net.posesor.UnallocatedChargeCreatedEvent;
import net.posesor.UnallocatedChargeRemovedEvent;
import net.posesor.UnallocatedExpenseAddedEvent;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@Component("UnallocatedPeriodsEventListener")
public final class EventListener {

    private final UnallocatedPeriodRepository repository;
    private final CommandBus commandBus;

    public EventListener(UnallocatedPeriodRepository repository, CommandBus commandBus) {
        this.repository = repository;
        this.commandBus = commandBus;
    }

    @EventHandler
    public void on(UnallocatedChargeCreatedEvent event) {
        val newItem = Entry.from(event);
        addOrUpdate(newItem);
    }

    @EventHandler
    public void on(UnallocatedExpenseAddedEvent event) {
        val newItem = Entry.from(event);
        addOrUpdate(newItem);
    }

    private void addOrUpdate(UnallocatedPeriod newItem) {
        val probe = new UnallocatedPeriod();
        probe.setSubjectId(newItem.getSubjectId());
        probe.setPrincipalName(newItem.getPrincipalName());
        probe.setPaymentTitle(newItem.getPaymentTitle());
        probe.setYear(newItem.getYear());
        probe.setMonth(newItem.getMonth());

        val example = Example.of(probe);
        val actual = repository.findOne(example);

        if (actual != null) {
            actual.setChargesTotal(actual.getChargesTotal().add(newItem.getChargesTotal()));
            actual.setExpensesTotal(actual.getExpensesTotal().add(newItem.getExpensesTotal()));
        }

        val toSave = actual == null
                ? newItem
                : actual;

        repository.save(toSave);

        commandBus.dispatch(asCommandMessage(new NotifySettlementsChangedCommand(toSave.getPrincipalName())));
    }

    @EventHandler
    public void on(UnallocatedChargeRemovedEvent event) {
        val probe = new UnallocatedPeriod();
        probe.setSubjectId(event.getSubjectId());
        probe.setPrincipalName(event.getPrincipalName());
        probe.setPaymentTitle(event.getPaymentTitle());
        probe.setYear(event.getPaymentDate().getYear());
        probe.setMonth(event.getPaymentDate().getMonthValue());
        val item = repository.findOne(Example.of(probe));
        val newAmount = item.getChargesTotal().subtract(event.getAmount());
        if (newAmount.compareTo(BigDecimal.ZERO) == 0) {
            repository.delete(item.getId());
        } else {
            probe.setChargesTotal(newAmount);
        }

        commandBus.dispatch(asCommandMessage(new NotifySettlementsChangedCommand(event.getPrincipalName())));
    }

}

@UtilityClass
class Entry {
    static UnallocatedPeriod from(UnallocatedChargeCreatedEvent event) {
        val result = new UnallocatedPeriod();
        result.setSubjectId(event.getSubjectId());
        result.setPrincipalName(event.getPrincipalName());
        result.setPaymentTitle(event.getPaymentTitle());
        result.setYear(event.getPaymentDate().getYear());
        result.setMonth(event.getPaymentDate().getMonthValue());
        result.setChargesTotal(event.getAmount());
        result.setExpensesTotal(BigDecimal.ZERO);
        return result;
    }

    static UnallocatedPeriod from(UnallocatedExpenseAddedEvent event) {
        val result = new UnallocatedPeriod();
        result.setSubjectId(event.getSubjectId());
        result.setPrincipalName(event.getPrincipalName());
        result.setPaymentTitle(event.getPaymentTitle());
        result.setYear(event.getPaymentDate().getYear());
        result.setMonth(event.getPaymentDate().getMonthValue());
        result.setChargesTotal(BigDecimal.ZERO);
        result.setExpensesTotal(event.getAmount());
        return result;
    }
}