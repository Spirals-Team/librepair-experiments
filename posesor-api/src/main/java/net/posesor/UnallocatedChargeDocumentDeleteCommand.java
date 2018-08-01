package net.posesor;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class UnallocatedChargeDocumentDeleteCommand {
    @TargetAggregateIdentifier
    private String documentId;
    private String principalName;
}
