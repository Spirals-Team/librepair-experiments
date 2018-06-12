package net.posesor.unallocated.periods;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnallocatedPeriodRepository extends MongoRepository<UnallocatedPeriod, String> { }
