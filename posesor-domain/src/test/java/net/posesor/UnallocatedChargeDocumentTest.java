package net.posesor;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class UnallocatedChargeDocumentTest {

    private AggregateTestFixture<UnallocatedChargeDocument> fixture = new AggregateTestFixture<>(UnallocatedChargeDocument.class);

    @Test
    public void shouldBeDeletable() {
        fixture
                .given(
                        new UnallocatedChargeDocumentCreatedEvent(
                                "id",
                                "principal name",
                                "subject id",
                                "subject name",
                                "account receivable id",
                                "customer name",
                                LocalDate.of(2001, 2, 3),
                                "payment title",
                                BigDecimal.ONE))
                .when(
                        new UnallocatedChargeDocumentDeleteCommand("id", "principal name"))
                .expectEvents(
                        new UnallocatedChargeDocumentDeletedEvent(
                                "id",
                                "principal name",
                                "subject id",
                                "account receivable id",
                                LocalDate.of(2001, 2, 3),
                                "payment title",
                                BigDecimal.ONE
                        )
                );

    }

}