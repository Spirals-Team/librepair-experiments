package net.posesor.allocations;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import java.math.BigDecimal;

/**
 * DTO model used as template for Allocations definition.
 */
@Data
public class AllocationsDefinitionDto {

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
    private YearMonth periodFrom = new YearMonth();

    /**
     * Settlement last day.
     */
    private YearMonth periodTo = new YearMonth();

    /**
     * Collection of documents included in the settlement.
     */
    private UnsettledPart[] items;

    /**
     * Helper method to simplify creation of {@link YearMonth}.
     *
     * @param year  value of {@link YearMonth#year}
     * @param month value of {@link YearMonth#month}
     * @return instance constructed from method's arguments.
     */
    public static YearMonth from(int year, int month) {
        val result = new YearMonth();
        result.setYear(year);
        result.setMonth(month);
        return result;
    }

    /**
     * Builder method to construct {@link UnsettledPart}.
     *
     * @return instance constructed from method's arguments.
     */
    public static UnsettledPart from(BigDecimal chargesTotal, BigDecimal expensesTotal) {
        val result = new UnsettledPart();
        result.setChargesAmount(chargesTotal);
        result.setExpensesAmount(expensesTotal);
        return result;
    }

    /**
     * Year and month representation for DTO.
     */
    @Data
    public static class YearMonth {
        private int year;
        private int month;

        static YearMonth of(int year, int month) {
                val result = new YearMonth();
                result.year = year;
                result.month = month;
                return result;
        }
    }

    // TODO rename to UnclearedPart and change API on client side.
    @Data
    @NoArgsConstructor
    public static class UnsettledPart {
        public static UnsettledPart of(BigDecimal chargesTotal, BigDecimal expensesTotal, YearMonth period) {
            val item = new UnsettledPart();
            item.setChargesAmount(chargesTotal);
            item.setExpensesAmount(expensesTotal);
            item.setPeriod(period);
            return item;
        }

        private BigDecimal chargesAmount;

        private BigDecimal expensesAmount;

        private YearMonth period;
    }
}
