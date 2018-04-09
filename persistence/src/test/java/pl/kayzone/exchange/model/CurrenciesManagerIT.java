package pl.kayzone.exchange.model;

import com.mongodb.MongoClient;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import pl.kayzone.exchange.model.entity.Currency;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CurrenciesManagerIT {
    Datastore ds;
    Query<Currency> query;
    MongoClient mongo;
    Morphia morphia;
    Datastore datastore;
    CurrenciesManager currenciesManager;
    TestClassCreator tcc;

    @Before
    public void setUp() {
       // MockitoAnnotations.initMocks(this);
        currenciesManager = new CurrenciesManager(new MongoClient(), new Morphia());
        this.tcc = new TestClassCreator();
    }

    @Test
    public void t1_testSave() throws Exception {
        currenciesManager.save(tcc.getCurrency());
        assertThatCode(() -> {
            currenciesManager.save(tcc.getCurrency());
        }).doesNotThrowAnyException();
    }

    @Test
    public void t2_testFindAll() throws Exception {
        List<Currency> result = currenciesManager.findAll();
        assertThat(result).isNotNull();
        if (result.size() >1 )
            assertThat(Arrays.<Currency>asList(tcc.getCurrency()).get(0).getIdCode()).isEqualTo(result.get(0).getIdCode());
       // Assert.assertEquals(Arrays.<Currency>asList(tcc.getCurrency()), result);
    }

    @Test
    public void t3_testFind() throws Exception {
        Currency result = currenciesManager.find(tcc.getCurrency().getIdCode());
        if (result == null) {
            throw  new Exception();
        }
        assertThat(result).isEqualToComparingOnlyGivenFields(tcc.getCurrency(),
                "idCode","name","urlNbp","tablesType");
    }

    @Test
    public void t4_testCreateOperations() throws Exception {
        UpdateOperations<Currency> result = currenciesManager.createOperations();
        assertThat(result).isInstanceOf(UpdateOperations.class);
    }

    @Test
    public void t5_testUpdate() throws Exception {

        UpdateResults result = currenciesManager.update(
                tcc.getCurrency(),
                currenciesManager.getDatastore(null).createUpdateOperations(Currency.class).inc("rates",1));
        assertThat(result.getUpdatedCount()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void t8_testRemove() throws Exception {
        currenciesManager.remove(new Currency("idCode", "name", "urlNbp", "tablesType", Double.valueOf(0)));
    }

    @Test
    public void t6_testGetDatastore() throws Exception {
        Datastore result = currenciesManager.getDatastore("conn");
        assertThat(result).isInstanceOf(Datastore.class);
    }

    @Test
    public void t7_testGetMorphia() throws Exception {
        Morphia result = currenciesManager.getMorphia();
    }

    @AfterClass
    public static void removeAllTestetObject() {
        CurrenciesManager cm = new CurrenciesManager(new MongoClient(),new Morphia());
        cm.getDs().delete(new TestClassCreator().getCurrency());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme