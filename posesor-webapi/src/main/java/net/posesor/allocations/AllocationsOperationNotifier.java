package net.posesor.allocations;

import lombok.Value;
import lombok.val;
import net.posesor.app.events.AllocationsCompleted;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Notifies when Allocation operation is finished.
 *
 * Notification operation is finished when: TBD
 * *
 */
@Component
public class AllocationsOperationNotifier {

    public static final String QUEUE = "/queue/allocations/operation";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @CommandHandler
    public void handle(AllocationsCompleted entry) {
        val notification = new FinishedDto(true);
        simpMessagingTemplate.convertAndSendToUser(entry.getPrincipalName(), QUEUE, notification);
    }

    @Value
    public static class FinishedDto {
        private boolean ignored;
    }
}
