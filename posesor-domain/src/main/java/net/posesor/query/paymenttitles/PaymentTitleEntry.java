package net.posesor.query.paymenttitles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PaymentTitleEntry")
@AllArgsConstructor
@NoArgsConstructor
public @Data class PaymentTitleEntry {
    @Id
    private String documentId;
    private String name;
    private String principalName;
    private Integer references;
}
