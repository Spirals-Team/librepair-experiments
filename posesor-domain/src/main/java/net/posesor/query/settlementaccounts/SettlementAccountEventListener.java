package net.posesor.query.settlementaccounts;

import lombok.val;
import net.posesor.domain.events.AccountsReceivableCreatedEvent;
import net.posesor.domain.events.ChargeRemovedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
public final class SettlementAccountEventListener {

    private SettlementAccountRepository repository;

    SettlementAccountEventListener(SettlementAccountRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(AccountsReceivableCreatedEvent evt) {
        val entry = new SettlementAccountEntry();
        entry.setSettlementAccountId(evt.getAccountId());
        entry.setName(evt.getCustomerName());
        entry.setPrincipalName(evt.getPrincipalName());
        entry.setSubjectId(evt.getSubjectId());
        repository.save(entry);
    }

    @EventHandler
    public void on(ChargeRemovedEvent event) {
        val probe = new SettlementAccountEntry();
        probe.setPrincipalName(event.getPrincipalName());
        probe.setSettlementAccountId(event.getSettlementAccountId());
        val template = Example.of(probe);

        val found = repository.findOne(template);
        if (found == null) return;

        repository.delete(found);

    }
}
