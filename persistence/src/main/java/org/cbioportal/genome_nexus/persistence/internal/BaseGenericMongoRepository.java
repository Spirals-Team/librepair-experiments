package org.cbioportal.genome_nexus.persistence.internal;

import com.mongodb.BulkWriteOperation;
import com.mongodb.DBObject;

import org.bson.Document;
import org.cbioportal.genome_nexus.persistence.GenericMongoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseGenericMongoRepository implements GenericMongoRepository
{
    protected final MongoTemplate mongoTemplate;

    public BaseGenericMongoRepository(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveDBObjects(String collection, List<DBObject> dbObjects)
    {
        // nothing to save, abort
        if (dbObjects == null || dbObjects.size() == 0) {
            return;
        }

        List<Document> documents = new LinkedList<Document>();
        for (DBObject dbObject : dbObjects) {
            documents.add(new Document(dbObject.toMap()));
        }

        this.mongoTemplate.getCollection(collection).insertMany(documents);
    }
}
