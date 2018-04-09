package io.recruiter.application.common.database.repositories;

import io.recruiter.application.common.database.model.JobPosition;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobPositionRepository extends MongoRepository<JobPosition, String> {
//    Optional<JobPosition> findById(String id);
}
