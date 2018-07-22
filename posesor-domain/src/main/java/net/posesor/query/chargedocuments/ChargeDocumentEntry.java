package net.posesor.query.chargedocuments;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ChargeDocumentEntry {
    @Id
    private String documentId;
    private String principalName;
    private String subjectId;
    private String subjectName;
    private String customerId;
    private String customerName;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
}
