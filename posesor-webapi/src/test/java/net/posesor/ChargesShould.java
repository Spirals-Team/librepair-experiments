package net.posesor;

import lombok.val;
import net.posesor.api.Charges;
import net.posesor.api.Subjects;
import net.posesor.charges.ChargeDocumentDto;
import net.posesor.subjects.SubjectDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChargesShould {

    @LocalServerPort
    private int port;

    @Test
    public void buildSubjectsList() {
        val userName = "demo " + UUID.randomUUID().toString();
        val customerName = "Customer name" + UUID.randomUUID().toString();
        val subjectName = "Subject name " + UUID.randomUUID().toString();
        val paymentTitle = "Payment title " + UUID.randomUUID().toString();

        val events = Subjects.API.createdEvent(port, userName);

        val dto = new ChargeDocumentDto(
                UUID.randomUUID().toString(),
                customerName,
                subjectName,
                "2001-02-03",
                paymentTitle,
                BigDecimal.ZERO
        );

        Charges.API.create(port, userName, dto);

        events.next();

        val suggestions = Subjects.API.suggest(port, userName, subjectName);

        Assertions
                .assertThat(suggestions)
                .containsExactlyInAnyOrder(subjectName);
    }
}
