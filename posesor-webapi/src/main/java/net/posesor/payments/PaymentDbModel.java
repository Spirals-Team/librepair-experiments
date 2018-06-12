package net.posesor.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Describe single Payment operation in Storage format.
 */
@Data
@Document(collection = "payments")
public class PaymentDbModel {
    @NotNull
    @NotEmpty
    private String subjectName;

    @Id
    private String paymentId;
    private String subjectId;
    private String customerName;
    private LocalDate paymentDate;
    private String accountName;
    private String description;
    private String principalName;
    private List<PaymentEntry> entries;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentEntry {
        private String paymentTitle;
        private BigDecimal amount;
    }
}
