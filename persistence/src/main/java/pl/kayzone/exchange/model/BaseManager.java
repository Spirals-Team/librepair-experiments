package pl.kayzone.exchange.model;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class  BaseManager<T> {
    protected String connectionString = "mongodb://127.0.0.1:27017/exchangeOffice";
    protected MongoClient mongo;
    protected final Morphia morphia;
    protected Datastore datastore;

    public BaseManager( MongoClient mc, Morphia m) {
        this.mongo = mc;
        this.morphia = getMorphia();
    }

    public Datastore getDatastore (String conn) {
        String dbname;
        if (datastore == null) {
            if (conn != null) {
                this.connectionString = conn;
                dbname = connectionString.substring(connectionString.lastIndexOf("/") + 1, connectionString.length());
            } else {
                dbname = "exchangeOffice";
            }
            datastore = morphia.createDatastore(mongo, dbname);
            datastore.ensureIndexes();
            return  datastore;
        } else
            return datastore;
    }

    public Morphia getMorphia() {
        final Morphia morphia = new Morphia();
                      morphia.mapPackage("pl.kayzone.exchange.model.entity");
        return morphia;
    }

    public String getConnectionString() {

        return this.connectionString;
    }

    public void save(T t) {
        if (t != null) {
            this.datastore.save(t);
        }
    }
 }

