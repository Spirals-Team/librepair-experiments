package net.posesor.accountcharges;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;


@EqualsAndHashCode
@NoArgsConstructor
public @Data class UnsettledAccountFinancialEntry {

    @Id
    private String id;

    private String principalName;

    private String settlementAccountId;

    private String paymentTitle;

    private BigDecimal chargesTotal;

    private BigDecimal expensesTotal;

    private Integer year;

    private Integer month;
}
