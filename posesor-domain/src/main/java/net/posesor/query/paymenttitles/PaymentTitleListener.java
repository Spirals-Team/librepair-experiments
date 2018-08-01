package net.posesor.query.paymenttitles;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.posesor.UnallocatedChargeDocument;
import net.posesor.UnallocatedChargeDocumentCreatedEvent;
import net.posesor.UnallocatedChargeDocumentDeleteCommand;
import net.posesor.UnallocatedChargeDocumentDeletedEvent;
import net.posesor.query.PaymentTitleView;
import net.posesor.query.PaymentTitlesQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PaymentTitleListener {

    @Autowired
    private PaymentTitleRepository repository;

    @EventHandler
    void on(UnallocatedChargeDocumentCreatedEvent evt) {
        addOrUpdate(evt.getPrincipalName(), evt.getPaymentTitle());
    }

    @EventHandler
    void on(UnallocatedChargeDocumentDeletedEvent evt) {
        deleteOrUpdate(evt.getPrincipalName(), evt.getPaymentTitle());
    }

    @EventHandler
    void on(UnallocatedChargeDocument.ContentChanged evt) {
        addOrUpdate(evt.getPrincipalName(), evt.getCurrent().getPaymentTitle());
        deleteOrUpdate(evt.getPrincipalName(), evt.getLast().getPaymentTitle());
    }

    private void addOrUpdate(String principalName, String paymentTitle) {
        val probe = new PaymentTitleEntry(null, paymentTitle, principalName, null);
        val example = Example.of(probe);
        val actual = repository.findOne(example);

        if (actual == null) {
            val entry = new PaymentTitleEntry(UUID.randomUUID().toString(), paymentTitle, principalName, 1);
            repository.insert(entry);
        } else {
            actual.setReferences(actual.getReferences() + 1);
            repository.save(actual);
        }
    }

    private void deleteOrUpdate(String principalName, String paymentTitle) {
        val probe = new PaymentTitleEntry(null, paymentTitle, principalName, null);
        val example = Example.of(probe);
        val actual = repository.findOne(example);

        if (actual == null) {
            val entry = new PaymentTitleEntry(UUID.randomUUID().toString(), paymentTitle, principalName, -1);
            repository.insert(entry);
        } else {
            if (actual.getReferences() == 1)
                repository.delete(actual);
            else {
                actual.setReferences(actual.getReferences() - 1);
                repository.save(actual);
            }
        }

    }

    @QueryHandler
    List<PaymentTitleView> respond(PaymentTitlesQuery query) {
        return repository
                .findByPrincipalNameAndNameLike(query.getPrincipalName(), query.getHint())
                .map(it -> new PaymentTitleView(it.getName()))
                .collect(Collectors.toList());
    }
}
