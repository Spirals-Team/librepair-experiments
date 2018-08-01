package net.posesor.expenses;

import lombok.Value;
import lombok.val;
import net.posesor.app.events.NotifyDocumentsChangedCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExpensesNotifier {

    public static final String QUEUE = "/queue/expenses";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @CommandHandler
    public void handle(NotifyDocumentsChangedCommand entry) {
        val notification = new DocumentsChangedDto(true);
        simpMessagingTemplate.convertAndSendToUser(entry.getPrincipalName(), QUEUE, notification);
    }

    @Value
    public static class DocumentsChangedDto {
        private boolean ignored;
    }
}
