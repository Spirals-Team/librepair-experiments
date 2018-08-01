package net.posesor;

import lombok.NoArgsConstructor;
import lombok.val;
import net.posesor.domain.events.AccountsReceivableChargedEvent;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

/***
 *  Updates a AccountReceivable and a Subject to keep them in sync with charges amount defined in just created
 *  ChargeDocument.
 */
@Saga
@NoArgsConstructor
public class ChargeDocumentCreateSaga implements Serializable {

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
    public void on(UnallocatedChargeDocumentCreatedEvent event) {

        documentId = event.getDocumentId();
        val settlementAccountId = event.getAccountReceivableId();
        subjectId = event.getSubjectId();
        principalName = event.getPrincipalName();
        paymentDate = event.getPaymentDate();
        paymentTitle = event.getPaymentTitle();
        amount = event.getAmount();

        val command = new AccountReceivable.AddChargeCommand(settlementAccountId, documentId, paymentDate, paymentTitle, amount);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "documentId")
    public void on(AccountsReceivableChargedEvent event) {

        val chargeSubjectCommand = new Subject.AddChargeCommand(subjectId,
                principalName,
                documentId,
                event.getSettlementAccountId(),
                paymentDate,
                paymentTitle,
                amount);
        commandBus.dispatch(asCommandMessage(chargeSubjectCommand), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "documentId")
    @EndSaga
    public void on(SubjectChargeAddedEvent event) {
        val command = new UnallocatedChargeDocument.BookDocumentCommand(event.getDocumentId());
        commandBus.dispatch(asCommandMessage(command));
    }

}
