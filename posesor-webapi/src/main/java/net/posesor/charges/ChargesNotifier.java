package net.posesor.charges;

import lombok.Value;
import lombok.val;
import net.posesor.app.events.DocumentsChanged;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChargesNotifier {

    public static final String QUEUE = "/queue/charges";
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @CommandHandler
    public void handle(DocumentsChanged entry) {
        val notification = new ChargeCreatedDto(true);
        simpMessagingTemplate.convertAndSendToUser(entry.getPrincipalName(), QUEUE, notification);
    }

    /**
     * Notifies when new Charge has been created.
     */
    @Value
    public static class ChargeCreatedDto {
        private boolean ignored;
    }
}
