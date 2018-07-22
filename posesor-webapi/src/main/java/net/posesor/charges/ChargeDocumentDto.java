package net.posesor.charges;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO model used to transfer CHARGE data between frontend and backend.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeDocumentDto {
    private String chargeId;
    private String customerName;
    private String subjectName;
    private String paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
