package net.posesor.domain.events;

import lombok.Value;

@Value
public class AccountsReceivableCreatedEvent {

    private String principalName;
    private String accountId;
    private String customerName;
    private String subjectId;

}
