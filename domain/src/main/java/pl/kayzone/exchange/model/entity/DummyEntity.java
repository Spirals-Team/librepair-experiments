package pl.kayzone.exchange.model.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(noClassnameStored = true)
public class DummyEntity {

    @Id
    private ObjectId id;

    @Property("version")
    private String version;


    public DummyEntity(String version) {
        this.version = version;
    }


    public ObjectId getId() {
        return id;
    }


    public void setId(ObjectId id) {
        this.id = id;
    }


    public String getVersion() {
        return version;
    }


    public void setVersion(String version) {
        this.version = version;
    }
}
