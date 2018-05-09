package net.posesor.allocations;

import com.mongodb.MongoClient;
import lombok.val;
import net.posesor.query.expensedocuments.ExpenseDocumentEntry;
import net.posesor.payments.PaymentDbModel;
import net.posesor.query.settlementaccounts.SettlementAccountEntry;
import net.posesor.subjectcharges.UnallocatedPeriod;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@TestConfiguration
public class MongoTestConfig {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        val template = new MongoTemplate(new MongoClient("localhost"), "posesorDb-IT");
        // to avoid creating bigger and bigger collections for testing
        // is worth to remove well-known collections
        template.dropCollection("axonDomainEvents");
        template.dropCollection("axonSnapshotEvents");
        template.dropCollection(SettlementAccountEntry.class);
        template.dropCollection(ExpenseDocumentEntry.class);
        template.dropCollection(PaymentDbModel.class);
        template.dropCollection("subjects");
        template.dropCollection(UnallocatedPeriod.class);
        return template;
    }
}
