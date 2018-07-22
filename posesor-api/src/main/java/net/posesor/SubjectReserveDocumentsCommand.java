package net.posesor;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.time.YearMonth;

/***
 * Finds and releases all documents covered by the scope defined in the command.
 */
@Value
public class SubjectReserveDocumentsCommand {
    @TargetAggregateIdentifier
    private String subjectId;
    private String correlationId;
    private String paymentTitle;
    private YearMonth periodFrom;
    private YearMonth periodTo;
}
