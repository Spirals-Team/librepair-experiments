package net.posesor.subjects;

import lombok.val;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Operations available on Subjects collection.
 * <p>
 * Every operation, covered by SubjectOperations is filtered by
 * Principal. It separates all requests for security reason.
 */
public final class SubjectOperations {

    private final MongoOperations operations;
    private final String principalName;

    public SubjectOperations(MongoOperations operations, String principalName) {
        this.operations = operations;
        this.principalName = principalName;
    }

    /**
     * returns already existing SubjectDbModel with provided name or adds and return if does not exist.
     *
     * @param subjectName name of subject which need to be found / created.
     * @return subject with required name.
     */
    public SubjectDbModel getOrAdd(String subjectName) {
        val entry = new SubjectDbModel();
        entry.setSubjectName(subjectName);
        entry.setPrincipalName(principalName);

        try {
            operations.insert(entry);
            return entry;
        } catch (DuplicateKeyException ex) {
            // TODO include case when subject was just deleted i we assume is is already
            // but it is - as said - deleted ...
            val criteria = Query
                    .query(Criteria.where("subjectName").is(subjectName)
                            .andOperator(Criteria.where("principalName").is(principalName)));
            return operations.findOne(criteria, SubjectDbModel.class);
        }
    }
}
