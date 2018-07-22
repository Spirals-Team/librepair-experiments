package net.posesor.query.subjects;

import lombok.val;
import net.posesor.SubjectCreatedEvent;
import net.posesor.api.commands.NotifySubjectCreatedCommand;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component("SubjectEventListener")
public final class EventListener {

    private SubjectRepository repository;
    private final CommandGateway commandBus;

    EventListener(SubjectRepository repository, CommandGateway commandBus) {
        this.repository = repository;
        this.commandBus = commandBus;
    }

    @EventHandler
    public void on(SubjectCreatedEvent evt) {
        val entry = new SubjectEntry();
        entry.setSubjectId(evt.getSubjectId());
        entry.setPrincipalName(evt.getPrincipalName());
        entry.setName(evt.getName());

        repository.save(entry);

        commandBus.send(new NotifySubjectCreatedCommand(evt.getPrincipalName()));
    }
}
