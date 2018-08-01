package net.posesor.allocations;

import lombok.Value;
import lombok.val;
import net.posesor.app.events.NotifySettlementsChangedCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class AllocationsNotifier {

    public static final String QUEUE = "/queue/allocations";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @CommandHandler
    public void handle(NotifySettlementsChangedCommand entry) {
        val notification = new SettlementsChangedDto(true);
        simpMessagingTemplate.convertAndSendToUser(entry.getPrincipalName(), QUEUE, notification);
    }

    @Value
    public static class SettlementsChangedDto {
        private boolean ignored;
    }
}
