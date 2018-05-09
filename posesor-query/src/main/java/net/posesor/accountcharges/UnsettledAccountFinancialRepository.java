package net.posesor.accountcharges;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnsettledAccountFinancialRepository extends MongoRepository<UnsettledAccountFinancialEntry, String> {
}
