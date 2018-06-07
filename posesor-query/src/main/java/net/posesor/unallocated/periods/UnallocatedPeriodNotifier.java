package net.posesor.unallocated.periods;

import lombok.Value;
import lombok.val;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class UnallocatedPeriodNotifier {

    public static final String QUEUE = "/queue/allocations/options";
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @CommandHandler
    public void handle(NotifyDocumentsChangedCommand entry) {
        val notification = new ChargeCreatedDto(true);
        simpMessagingTemplate.convertAndSendToUser(entry.getPrincipalName(), QUEUE, notification);
    }

    @Value
    public static class NotifyDocumentsChangedCommand {
        private String principalName;
    }

    /**
     * Notifies when new Charge has been created.
     */
    @Value
    public static class ChargeCreatedDto {
        private boolean ignored;
    }
}
