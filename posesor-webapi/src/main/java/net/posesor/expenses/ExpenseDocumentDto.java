package net.posesor.expenses;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor // required for reconstruction as DTO
public final class ExpenseDocumentDto {
    private String subjectName;
    private String customerName;
    private String paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
    private String description;
}
