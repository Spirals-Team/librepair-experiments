package pl.kayzone.exchange.model;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kayzone.exchange.model.entity.Transaction;

public class TransactionManager extends BaseManager {

    private final Datastore ds;
    private Query<Transaction> query;
    private static final Logger LOGG = LoggerFactory.getLogger(CurrenciesManager.class);

    public TransactionManager(MongoClient mc, Morphia m) {
        super(mc, m);
        this.ds= super.getDatastore("exchangeOffice");
        query = super.getDatastore(getConnectionString()).createQuery(Transaction.class);
    }
    public void save(Transaction trans) {
        super.save(trans);
    }

    public Datastore getDs() {
        return this.ds;
    }

}
