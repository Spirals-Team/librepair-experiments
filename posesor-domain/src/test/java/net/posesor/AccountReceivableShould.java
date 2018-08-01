package net.posesor;

import net.posesor.domain.events.AccountsReceivableCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class AccountReceivableShould {

    private FixtureConfiguration<AccountReceivable> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(AccountReceivable.class);
    }

    @Test
    public void createInstance() {
        fixture
                .givenNoPriorActivity()
                .when(new AccountReceivable.CreateCommand("id", "customerName", "principalName", "subjectId"))
                .expectEvents(new AccountsReceivableCreatedEvent("principalName", "id", "customerName", "subjectId"));

    }


}