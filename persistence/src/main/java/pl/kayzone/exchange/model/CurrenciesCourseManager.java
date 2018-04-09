package pl.kayzone.exchange.model;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kayzone.exchange.model.entity.Currency;
import pl.kayzone.exchange.model.entity.CurrencyCourse;

import java.util.List;

public class CurrenciesCourseManager extends BaseManager {

    private Datastore ds;
    private Query<CurrencyCourse> query;
    private static final Logger LOGG = LoggerFactory.getLogger(CurrenciesManager.class);

    CurrenciesCourseManager(MongoClient mc, Morphia m) {
        super(mc,m);
        this.ds = super.getDatastore("exchangeOffice");
        query = getDatastore(getConnectionString()).createQuery(CurrencyCourse.class);
    }

    public Datastore getDs() {
        return ds;
    }

    public void save(CurrencyCourse cc) throws MongoException {
           super.save(cc);
    }

    public List<CurrencyCourse> findAll() {
        return query.asList();
    }

    public CurrencyCourse findActualCourse(String code) {
        ds.find(CurrencyCourse.class).disableValidation();
        query.field("active").equal(true)
             .field("idCode").equal(ds.get(Currency.class,code) );
        return query.get();
    }
    public UpdateOperations<CurrencyCourse> createOperations() {
        return ds.createUpdateOperations(CurrencyCourse.class);
    }
    public UpdateResults update(Query<CurrencyCourse> currencyCourse, UpdateOperations<CurrencyCourse> operations) {
        return ds.update(currencyCourse, operations);
    }

    public void remove (CurrencyCourse curr) {
        ds.delete(curr);
    }
}
