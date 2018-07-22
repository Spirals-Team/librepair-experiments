package net.posesor;

import lombok.NoArgsConstructor;
import lombok.val;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;
import static org.axonframework.eventhandling.saga.SagaLifecycle.end;

@Saga
@NoArgsConstructor
public final class ChargeDocumentUpdateSaga {

    private transient CommandGateway commandBus;

    @Autowired
    public void setCommandBus(CommandGateway value) {
        this.commandBus = value;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "documentId")
    public void on(UnallocatedChargeDocument.ContentChanged event) {
        val documentId = event.getDocumentId();

        {
            val command = new AccountReceivable.RemoveChargeCommand(event.getLast().getAccountReceivableId(),
                    documentId);
            commandBus.send(asCommandMessage(command), LoggingCallback.INSTANCE);
        }

        {
            val command = new AccountReceivable.AddChargeCommand(event.getCurrent().getAccountReceivableId(),
                    documentId,
                    event.getCurrent().getPaymentDate(),
                    event.getCurrent().getPaymentTitle(),
                    event.getCurrent().getAmount());
            commandBus.send(asCommandMessage(command), LoggingCallback.INSTANCE);
        }





        val removeCommand = new Subject.RemoveChargeCommand(
                event.getLast().getSubjectId(), event.getDocumentId(),
                event.getLast().getPaymentDate(), event.getLast().getPaymentTitle(), event.getLast().getAmount());
        commandBus.send(asCommandMessage(removeCommand), LoggingCallback.INSTANCE);

        val addCommand = new Subject.AddChargeCommand(
                event.getCurrent().getSubjectId(), event.getPrincipalName(), event.getDocumentId(),
                event.getCurrent().getAccountReceivableId(),
                event.getCurrent().getPaymentDate(), event.getCurrent().getPaymentTitle(), event.getCurrent().getAmount());
        commandBus.send(asCommandMessage(addCommand), LoggingCallback.INSTANCE);

        end();
    }
}