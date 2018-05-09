package net.posesor.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class PaymentDto {
    private String paymentId;
    private String subjectName;
    private String customerName;
    private String paymentDate;
    private String bankAccountName;
    private String description;
    private PaymentEntry[] entries;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentEntry {
        private BigDecimal amount;
        private String paymentTitle;
    }
}
