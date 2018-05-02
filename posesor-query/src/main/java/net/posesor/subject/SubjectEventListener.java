package net.posesor.subject;

import lombok.val;
import net.posesor.SubjectCreatedEvent;
import net.posesor.SubjectEntry;
import net.posesor.SubjectRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public final class SubjectEventListener {

    private SubjectRepository repository;

    SubjectEventListener(SubjectRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(SubjectCreatedEvent evt) {
        val entry = new SubjectEntry();
        entry.setSubjectId(evt.getSubjectId());
        entry.setPrincipalName(evt.getPrincipalName());
        entry.setName(evt.getName());

        repository.save(entry);
    }
}
