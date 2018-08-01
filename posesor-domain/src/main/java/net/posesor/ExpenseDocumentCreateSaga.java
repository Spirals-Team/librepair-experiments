package net.posesor;

import lombok.NoArgsConstructor;
import lombok.val;
import net.posesor.domain.events.ExpenseDocumentCreatedEvent;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;
import static org.axonframework.eventhandling.saga.SagaLifecycle.end;

/***
 *  Updates a SettlementAccount and a Subject into keep them in sync with charges amount defined in just created
 *  ChargeDocument.
 */
@Saga
@NoArgsConstructor
public class ExpenseDocumentCreateSaga implements Serializable {

    private transient CommandBus commandBus;

    @Autowired
    public void setCommandBus(CommandBus value) {
        this.commandBus = value;
    }

    private String documentId;
    private String subjectId;
    private String principalName;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;


    @StartSaga
    @SagaEventHandler(associationProperty = "documentId")
    public void on(ExpenseDocumentCreatedEvent event) {

        documentId = event.getDocumentId();
        subjectId = event.getSubjectId();
        principalName = event.getPrincipalName();
        paymentDate = event.getPaymentDate();
        paymentTitle = event.getPaymentTitle();
        amount = event.getAmount();

        val chargeSubjectCommand = new Subject.AddExpenseCommand(subjectId,
                principalName,
                documentId,
                paymentDate,
                paymentTitle,
                amount);
        commandBus.dispatch(asCommandMessage(chargeSubjectCommand), LoggingCallback.INSTANCE);

        end();
    }
}
