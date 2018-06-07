package net.posesor.query;

import lombok.Value;

import java.math.BigDecimal;
import java.time.YearMonth;

@Value
public class UnallocatedDocumentsView {
    private String subjectName;
    private String paymentTitle;
    private YearMonth period;
    private BigDecimal chargesTotal;
    private BigDecimal expensesTotal;
}