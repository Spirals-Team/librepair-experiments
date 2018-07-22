package net.posesor.query.expensedocuments;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseDocumentRepository extends MongoRepository<ExpenseDocumentEntry, String> {
}
