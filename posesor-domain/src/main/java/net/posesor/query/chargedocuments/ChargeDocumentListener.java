package net.posesor.query.chargedocuments;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.posesor.*;
import net.posesor.app.events.DocumentsChanged;
import net.posesor.domain.events.AllocationDocumentCreatedEvent;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@Component
@Slf4j
public final class ChargeDocumentListener {

    private final ChargeDocumentRepository repository;
    private final CommandBus commandBus;

    public ChargeDocumentListener(ChargeDocumentRepository repository,
                                  CommandBus commandBus) {
        this.repository = repository;
        this.commandBus = commandBus;
    }

    @EventHandler
    public void on(AllocationDocumentCreatedEvent event) {
        val model = ChargeCreatedModel.builder()
                .documentId(event.getDocumentId())
                .principalName(event.getPrincipalName())
                .subjectId(event.getSubjectId())
                .subjectName(event.getSubjectName())
                .customerId(event.getCustomerId())
                .customerName(event.getCustomerName())
                .paymentDate(event.getPaymentDate())
                .paymentTitle(event.getPaymentTitle())
                .amount(event.getAmount())
                .build();
        on(model);
    }

    @EventHandler
    public void on(UnallocatedChargeDocumentCreatedEvent event) {
        val model = ChargeCreatedModel.builder()
                .documentId(event.getDocumentId())
                .principalName(event.getPrincipalName())
                .subjectId(event.getSubjectId())
                .subjectName(event.getSubjectName())
                .customerId(event.getAccountReceivableId())
                .customerName(event.getCustomerName())
                .paymentDate(event.getPaymentDate())
                .paymentTitle(event.getPaymentTitle())
                .amount(event.getAmount())
                .build();
        on(model);
    }

    public void on(ChargeCreatedModel model) {
        val entry = new ChargeDocumentEntry();
        entry.setDocumentId(model.getDocumentId());
        entry.setPrincipalName(model.getPrincipalName());
        entry.setSubjectId(model.getSubjectId());
        entry.setSubjectName(model.getSubjectName());
        entry.setCustomerId(model.getCustomerId());
        entry.setCustomerName(model.getCustomerName());
        entry.setPaymentDate(model.getPaymentDate());
        entry.setPaymentTitle(model.getPaymentTitle());
        entry.setAmount(model.getAmount());

        repository.save(entry);

        val notification = new DocumentsChanged(entry.getPrincipalName());
        commandBus.dispatch(asCommandMessage(notification));
    }

    @EventHandler
    public void on(UnallocatedChargeDocumentDeletedEvent event) {
        repository.delete(event.getDocumentId());

        val notification = new DocumentsChanged(event.getPrincipalName());
        commandBus.dispatch(asCommandMessage(notification));
    }

    @EventHandler
    public void on(UnallocatedChargeDocumentUpdatedEvent event) {
        val entry = repository.findOne(event.getDocumentId());
        if (entry == null) return;

        entry.setSubjectId(event.getSubjectId());
        entry.setSubjectName(event.getSubjectName());
        entry.setCustomerId(event.getCustomerId());
        entry.setCustomerName(event.getCustomerName());
        entry.setPaymentDate(event.getPaymentDate());
        entry.setPaymentTitle(event.getPaymentTitle());
        entry.setAmount(event.getAmount());

        repository.save(entry);

        val notification = new DocumentsChanged(entry.getPrincipalName());
        commandBus.dispatch(asCommandMessage(notification));
    }

    @Value
    @Builder
    public static class ChargeCreatedModel {
        private String documentId;
        private String principalName;
        private String subjectId;
        private String subjectName;
        private String customerId;
        private String customerName;
        private LocalDate paymentDate;
        private String paymentTitle;
        private BigDecimal amount;
    }

}
