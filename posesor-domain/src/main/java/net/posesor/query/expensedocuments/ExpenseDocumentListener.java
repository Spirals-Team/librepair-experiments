package net.posesor.query.expensedocuments;

import lombok.val;
import net.posesor.domain.events.ExpenseDocumentCreatedEvent;
import net.posesor.domain.events.ExpenseDocumentDeletedEvent;
import net.posesor.domain.events.ExpenseDocumentUpdatedEvent;
import net.posesor.app.events.NotifyDocumentsChangedCommand;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@Component
public class ExpenseDocumentListener {

    private final ExpenseDocumentRepository repository;
    private final CommandBus commandBus;

    public ExpenseDocumentListener(ExpenseDocumentRepository repository, CommandBus commandBus) {
        this.repository = repository;
        this.commandBus = commandBus;
    }

    @EventHandler
    public void on(ExpenseDocumentCreatedEvent event) {
        val entry = new ExpenseDocumentEntry();
        entry.setDocumentId(event.getDocumentId());
        entry.setPrincipalName(event.getPrincipalName());
        entry.setCustomerName(event.getCustomerName());
        entry.setSubjectName(event.getSubjectName());
        entry.setPaymentDate(event.getPaymentDate());
        entry.setPaymentTitle(event.getPaymentTitle());
        entry.setAmount(event.getAmount());
        entry.setDescription(event.getDescription());

        repository.save(entry);

        val notification = new NotifyDocumentsChangedCommand(entry.getPrincipalName());
        commandBus.dispatch(asCommandMessage(notification));
    }

    @EventHandler
    public void on(ExpenseDocumentDeletedEvent event) {
        repository.delete(event.getExpenseDocumentId());

        val notification = new NotifyDocumentsChangedCommand(event.getPrincipalName());
        commandBus.dispatch(asCommandMessage(notification));
    }

    @EventHandler
    public void on(ExpenseDocumentUpdatedEvent event) {
        val entry = repository.findOne(event.getExpenseDocumentId());
        entry.setDocumentId(event.getExpenseDocumentId());
        entry.setSubjectName(event.getSubjectName());
        entry.setCustomerName(event.getCustomerName());
        entry.setPaymentDate(event.getPaymentDate());
        entry.setPaymentTitle(event.getPaymentTitle());
        entry.setAmount(event.getAmount());
        entry.setDescription(event.getDescription());

        repository.save(entry);

        val notification = new NotifyDocumentsChangedCommand(entry.getPrincipalName());
        commandBus.dispatch(asCommandMessage(notification));
    }
}
