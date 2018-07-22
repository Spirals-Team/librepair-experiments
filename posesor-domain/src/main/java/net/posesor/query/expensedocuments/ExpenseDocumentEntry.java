package net.posesor.query.expensedocuments;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "ExpenseDocument")
public @Data class ExpenseDocumentEntry {
    @Id
    private String documentId;
    private String principalName;
    private String subjectName;
    private String customerName;
    private LocalDate paymentDate;
    private String paymentTitle;
    private BigDecimal amount;
    private String description;
}
