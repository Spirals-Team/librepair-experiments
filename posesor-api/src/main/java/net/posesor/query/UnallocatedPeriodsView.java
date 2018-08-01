package net.posesor.query;

import lombok.Builder;
import lombok.Value;

import java.time.YearMonth;

@Builder
@Value
public class UnallocatedPeriodsView {
    private String subjectId;
    private String paymentTitle;
    private YearMonth periodFrom;
    private YearMonth periodTo;
}