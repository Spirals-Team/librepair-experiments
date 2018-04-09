package pl.kayzone.exchange.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.omg.CORBA.Environment;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

public interface MongoCofiguration  {
    MongoCofiguration using(Environment env);
    MongoCofiguration with (Morphia morphia);
    Datastore buildDatastore();

}
