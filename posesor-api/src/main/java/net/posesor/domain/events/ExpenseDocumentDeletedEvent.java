package net.posesor.domain.events;

import lombok.Value;

@Value
public final class ExpenseDocumentDeletedEvent {
    private String expenseDocumentId;
    private String principalName;
}
