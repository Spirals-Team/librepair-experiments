package net.posesor.payments;


import lombok.val;
import net.posesor.allocations.MongoTestConfig;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@ContextConfiguration(classes = {MongoTestConfig.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PaymentsOperationsShould {

    @Autowired
    private MongoOperations provider;

    @Test
    public void updateDocument() {
        val principalName = UUID.randomUUID().toString();
        val repo = new PaymentOperations(provider, principalName);
        val initial = Given.createSimplePaymentDbModel();
        repo.insert(initial);

        val update = Given.createFullPaymentDbModel();
        update.setPaymentId(initial.getPaymentId());

        repo.update(update);

        Assertions.assertThat(repo.get(initial.getPaymentId())).isEqualTo(update);
    }

    @Test
    public void insertDocument() {
        val principalName = UUID.randomUUID().toString();
        val repo = new PaymentOperations(provider, principalName);

        val expected = new PaymentDbModel();

        repo.insert(expected);
        Assertions.assertThat(expected.getPaymentId()).isNotNull();

        {
            // assert returns model
            val actual = repo.get(expected.getPaymentId());
            Assertions.assertThat(actual).isNotSameAs(expected);
            Assertions.assertThat(actual.getPaymentId()).isEqualTo(expected.getPaymentId());
        }

        {
            // assert separated repo can't get model
            val separatedRepo = new PaymentOperations(provider, UUID.randomUUID().toString());
            Assertions.assertThat(separatedRepo.get(expected.getPaymentId())).isNull();
        }
    }

    @Test
    public void remove() {
        val principalName = UUID.randomUUID().toString();
        val repo = new PaymentOperations(provider, principalName);
        val initial = Given.createSimplePaymentDbModel();
        repo.insert(initial);

        val paymentId = initial.getPaymentId();

        repo.remove(paymentId);

        Assertions.assertThat(repo.get(paymentId)).isEqualTo(null);
    }

    @Test
    public void getbyPaimentId() {
        val principalName = UUID.randomUUID().toString();
        val repo = new PaymentOperations(provider, principalName);
        val initial = Given.createSimplePaymentDbModel();
        repo.insert(initial);

        val paymentId = initial.getPaymentId();

        Assertions.assertThat(repo.get(paymentId)).isEqualTo(initial);

    }

    @Test
    public void getAll() {
        val principalName = UUID.randomUUID().toString();
        val repo = new PaymentOperations(provider, principalName);

        val first = Given.createSimplePaymentDbModel();
        val second = Given.createSimplePaymentDbModel();
        val three = Given.createSimplePaymentDbModel();
        repo.insert(first);
        repo.insert(second);
        repo.insert(three);

        val all = repo.getAll();
        Assertions.assertThat(repo.getAll().size()).isEqualTo(3);
    }

    @Test
    public void getByPaymentTitle(){
        val principalName = UUID.randomUUID().toString();
        val repo = new PaymentOperations(provider, principalName);
        val initial = Given.createSimplePaymentDbModel();
        repo.insert(initial);

        val paymentTitleInitial = initial.getEntries().get(0).getPaymentTitle();

        Assertions.assertThat(repo.getByPaymentTitle(paymentTitleInitial)).contains(initial);
    }

    @Test
    public void getLatest() {
        val principalName = UUID.randomUUID().toString();
        val repo = new PaymentOperations(provider, principalName);

        val latest = repo.getLatests();
        Assertions.assertThat(latest).isNotNull();
    }
}
