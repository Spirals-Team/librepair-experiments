package net.posesor;

import lombok.Value;

/***
 * Costs and unallocatedCharges are reserved for an allocation.
 */
@Value
public class SubjectDocumentsReservedEvent {
    private String correlationId;
    private String subjectId;
    private ExpenseDocumentId[] expenseDocumentIds;
    private ChargeDocumentId[] chargeDocumentsIds;

    @Value
    public static class ExpenseDocumentId {
        private String value;
    }

    @Value
    public static class ChargeDocumentId {
        private String value;
    }

}
