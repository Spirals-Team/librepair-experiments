package net.posesor.unallocated.periods;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;


@EqualsAndHashCode
@NoArgsConstructor
public @Data class UnallocatedPeriod {

    @Id
    private String id;

    private String principalName;
    private String subjectId;
    private String paymentTitle;
    private BigDecimal chargesTotal;
    private BigDecimal expensesTotal;
    private Integer year;
    private Integer month;
}
