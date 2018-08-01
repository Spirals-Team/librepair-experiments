package net.posesor.allocations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import java.math.BigDecimal;

/**
 * DTO model used as template for Allocations definition.
 */
@Data
public class AllocationDocumentsDto {

    private String subjectName;
    private String paymentTitle;
    private DocumentData[] items;

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
     * Builder method to construct {@link DocumentData}.
     *
     * @return instance constructed from method's arguments.
     */
    public static DocumentData from(BigDecimal chargesTotal, BigDecimal expensesTotal) {
        val result = new DocumentData();
        result.setChargesAmount(chargesTotal);
        result.setExpensesAmount(expensesTotal);
        return result;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class YearMonth {
        private int year;
        private int month;

        public static YearMonth of(int year, int month) {
                val result = new YearMonth();
                result.year = year;
                result.month = month;
                return result;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DocumentData {
        private BigDecimal chargesAmount;
        private BigDecimal expensesAmount;
        private YearMonth period;
    }
}
