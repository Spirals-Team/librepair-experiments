package net.posesor.unallocated.documents;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryRepository extends MongoRepository<UnallocatedDocument, String> {
}
