package net.posesor.query.settlementaccounts;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SettlementEntry")
public @Data class SettlementAccountEntry {
    @Id
    private String settlementAccountId;
    private String principalName;
    private String subjectId;
    private String name;
}
