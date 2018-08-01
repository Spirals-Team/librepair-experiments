package net.posesor.subjects;

import lombok.Value;
import lombok.val;
import net.posesor.app.events.SubjectCreated;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SubjectsNotifier {

    public static final String QUEUE = "/queue/subjects";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @CommandHandler
    public void handle(SubjectCreated entry) {
        val notification = new SubjectCreatedDto(0);
        simpMessagingTemplate.convertAndSendToUser(entry.getPrincipalName(), QUEUE, notification);
    }

    @Value
    public static class SubjectCreatedDto {
        private int ignored;
    }

}
