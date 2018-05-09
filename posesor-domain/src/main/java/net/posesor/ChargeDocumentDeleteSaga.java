package net.posesor;

import lombok.NoArgsConstructor;
import lombok.val;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import static org.axonframework.eventhandling.saga.SagaLifecycle.end;

@Saga
@NoArgsConstructor
public class ChargeDocumentDeleteSaga {

    private transient CommandGateway commandGateway;

    @Autowired
    public void setCommandGateway(CommandGateway value) {
        this.commandGateway = value;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "documentId")
    public void on(UnallocatedChargeDocumentDeletedEvent e) {

        {
            val cmd = new Subject.RemoveChargeCommand(e.getSubjectId(),
                    e.getDocumentId(),
                    e.getPaymentDate(),
                    e.getPaymentTitle(),
                    e.getAmount());
            commandGateway.send(cmd, LoggingCallback.INSTANCE);
        }

        {
            val cmd = new AccountReceivable.RemoveChargeCommand(e.getAccountReceivableId(), e.getDocumentId());
            commandGateway.send(cmd, LoggingCallback.INSTANCE);

        }

        end();
    }

}
