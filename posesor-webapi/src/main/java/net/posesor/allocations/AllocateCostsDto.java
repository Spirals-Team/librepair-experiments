package net.posesor.allocations;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import java.time.LocalDate;
import java.time.YearMonth;

@Data
@NoArgsConstructor
public final class AllocateCostsDto {
    private String notificationToken;
    private String subjectName;
    private String paymentTitle;
    private int yearFrom;
    private int monthFrom;
    private int yearTo;
    private int monthTo;
    private int operationYear;
    private int operationMonth;
    private int operationDay;

    public static AllocateCostsDto of(String subjectName, String paymentTitle, YearMonth from, YearMonth to, LocalDate operation) {
        val result = new AllocateCostsDto();
        result.subjectName = subjectName;
        result.paymentTitle = paymentTitle;
        result.yearFrom = from.getYear();
        result.monthFrom = from.getMonthValue();
        result.yearTo = to.getYear();
        result.monthTo = to.getMonthValue();
        result.operationYear = operation.getYear();
        result.operationMonth = operation.getMonthValue();
        result.operationDay = operation.getDayOfMonth();
        return result;
    }
}
