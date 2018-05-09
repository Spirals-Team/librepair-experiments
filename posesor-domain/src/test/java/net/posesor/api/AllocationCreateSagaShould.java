package net.posesor.api;

import net.posesor.AllocationAggregate;
import net.posesor.AllocationCreateSaga;
import net.posesor.SubjectReserveDocumentsCommand;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Test;

import java.time.LocalDate;
import java.time.YearMonth;

public class AllocationCreateSagaShould {

    SagaTestFixture<AllocationCreateSaga> sut = new SagaTestFixture<>(AllocationCreateSaga.class);

    @Test
    public void reserveRelatedDocuments() {
        sut
                .givenNoPriorActivity()
                .whenAggregate("source")
                .publishes(new AllocationAggregate.CreatedEvent(
                        "principal name",
                        "correlation id",
                        "subject id",
                        "payment title",
                        YearMonth.of(2001,1),
                        YearMonth.of(2001,2),
                        LocalDate.of(2001,1,31)))
                .expectDispatchedCommands(new SubjectReserveDocumentsCommand(
                        "subject id", "correlation id", "payment title", YearMonth.of(2001, 1), YearMonth.of(2001,2)
                ));
    }
}