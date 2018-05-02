package net.posesor.subjectcharges;

import lombok.val;
import net.posesor.NotifySettlementsChangedCommand;
import net.posesor.UnallocatedChargeCreatedEvent;
import net.posesor.UnallocatedChargeRemovedEvent;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@Component
public final class UnallocatedPeriodEventListener {

    private final UnallocatedPeriodRepository repository;
    private final CommandBus commandBus;

    public UnallocatedPeriodEventListener(UnallocatedPeriodRepository repository,
                                          CommandBus commandBus)
    {
        this.repository = repository;
        this.commandBus = commandBus;
    }

    @EventHandler
    public void on(UnallocatedChargeCreatedEvent event) {
        val subjectId = event.getSubjectId();
        val principalName = event.getPrincipalName();
        val paymentTitle = event.getPaymentTitle();
        val year = event.getPaymentDate().getYear();
        val month = event.getPaymentDate().getMonthValue();

        val probe = new UnallocatedPeriod();
        probe.setSubjectId(subjectId);
        probe.setPrincipalName(principalName);
        probe.setPaymentTitle(paymentTitle);
        probe.setYear(year);
        probe.setMonth(month);
        val example = Example.of(probe);
        val actual = repository.findOne(example);

        val newEntry = actual == null;
        val entry = newEntry
                ? new UnallocatedPeriod()
                : actual;

        if (newEntry) {
            entry.setId(UUID.randomUUID().toString());
            entry.setPrincipalName(principalName);
            entry.setSubjectId(subjectId);
            entry.setPaymentTitle(paymentTitle);
            entry.setYear(year);
            entry.setMonth(month);
        }

        entry.setChargesTotal(Optional.ofNullable(entry.getChargesTotal()).orElse(BigDecimal.ZERO).add(event.getAmount()));
        repository.save(entry);

        commandBus.dispatch(asCommandMessage(new NotifySettlementsChangedCommand(event.getPrincipalName())));
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
        } else
        {
            probe.setChargesTotal(newAmount);
        }

        commandBus.dispatch(asCommandMessage(new NotifySettlementsChangedCommand(event.getPrincipalName())));
    }
}
