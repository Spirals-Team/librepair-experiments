package net.posesor;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Marks already reserved documents as allocated.
 */
@Value
public class SubjectAllocateReservedDocumentsCommand {
    @TargetAggregateIdentifier
    private String subjectId;
    private String correlationId;
}
