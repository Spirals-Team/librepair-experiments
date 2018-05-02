package net.posesor.allocations;

import lombok.*;

import java.math.BigDecimal;
import java.time.YearMonth;

/**
 * Domain model used as template for Settlement definition.
 */
@Data
public class SettlementDefinition {

    /**
     * Identifiable name of the subject accountable to pay costs.
     */
    private String subjectName;

    /**
     * Name of payment title.
     */
    private String paymentTitle;

    /**
     * Settlement starting date.
     */
    private YearMonth dateFrom;

    /**
     * Settlement last day.
     */
    private YearMonth dateTo;

    /**
     * set of expenses included in the settlement.
     */
    @Singular
    private UnsettledPart[] expenses;

    /**
     * set of charges included in the settlement.
     */
    @Singular
    private UnsettledPart[] charges;

    /**
     * EXPENSE-related data used as part of {@link SettlementDefinition}.
     */
    @Data
    public static class UnsettledPart {

        /**
         * Id of cleared document.
         */
        private String documentId;

        /**
         * value of uncleared document's amount.
         */
        private BigDecimal amount;

        public UnsettledPart() {

        }

        public UnsettledPart(String documentId, BigDecimal amount) {
            this();
            this.documentId = documentId;
            this.amount = amount;
        }

    }

}
