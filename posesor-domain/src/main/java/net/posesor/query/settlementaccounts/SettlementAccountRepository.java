package net.posesor.query.settlementaccounts;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SettlementAccountRepository extends MongoRepository<SettlementAccountEntry, String> {
    Collection<SettlementAccountEntry> findByNameStartsWithIgnoreCaseAndPrincipalName(String name, String principalName);
}
