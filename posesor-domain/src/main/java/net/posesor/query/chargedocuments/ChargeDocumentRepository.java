package net.posesor.query.chargedocuments;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeDocumentRepository extends MongoRepository<ChargeDocumentEntry, String> {
}
