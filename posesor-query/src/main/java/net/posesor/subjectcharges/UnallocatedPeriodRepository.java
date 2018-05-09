package net.posesor.subjectcharges;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnallocatedPeriodRepository extends MongoRepository<UnallocatedPeriod, String> { }
