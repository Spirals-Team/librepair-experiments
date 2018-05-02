package net.posesor;

import lombok.Value;

@Value
public class AccountsReceivableCreatedEvent {

    private String principalName;
    private String settlementAccountId;
    private String customerName;
    private String subjectId;

}
