package pl.kayzone.exchange.model;

import com.mongodb.MongoClient;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import pl.kayzone.exchange.model.entity.CurrencyCourse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CurrenciesCourseManagerIT {
    Datastore ds;
    Query<CurrencyCourse> query;
    MongoClient mongo;
    Morphia morphia;
    Datastore datastore;
    CurrenciesCourseManager currenciesCourseManager;
    TestClassCreator tcc;
    CurrenciesManager cm;

    @Before
    public void setUp() {
        mongo = new MongoClient();
        morphia = new Morphia();
        currenciesCourseManager = new CurrenciesCourseManager(mongo,morphia);
        tcc = new TestClassCreator();
        cm  = new CurrenciesManager(mongo,morphia);
        datastore = cm.getDatastore("mongodb://127.0.0.1:27017/exchangeOffice");
        cm.save(tcc.getCurrency());
    }

    @Test
    public void t01_testSave() throws Exception {
        CurrencyCourse ccm = tcc.getCurrencyCourse();
        currenciesCourseManager.save(ccm);
    }

    @Test
    public void t02_testFindAll() throws Exception {
        List<CurrencyCourse> result = currenciesCourseManager.findAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void t03_testFindActualCourse() throws Exception {
        CurrencyCourse result = currenciesCourseManager.findActualCourse("USD");
        System.out.println(result.getValidTo());
      //  assertThat(result.getIdCode()).isEqualTo(tcc.getCurrency());
        assertThat(result.getActive()).isEqualTo(true);
    }


    @Test
    public void t10_testUpdate() throws Exception {

        currenciesCourseManager.save(tcc.getCurrencyCourse());

        List<CurrencyCourse> lista = currenciesCourseManager.getDs().createQuery(CurrencyCourse.class).asList();
        for (CurrencyCourse cc : lista) {
            System.out.println(cc.toString());
        }
        Query<CurrencyCourse> query = currenciesCourseManager.getDatastore(null).createQuery(CurrencyCourse.class)
                                .field("_id").equal(lista.get(0).getId());

        UpdateOperations<CurrencyCourse> update = currenciesCourseManager.createOperations().inc("bid").inc("ask");
        UpdateResults result = currenciesCourseManager.update(query, update);

        Assert.assertEquals(result.getUpdatedCount(), 1);

    }

    @Test
    public void t15_testRemove() throws Exception {
        Query<CurrencyCourse> query = currenciesCourseManager.getDs().find(CurrencyCourse.class);
        currenciesCourseManager.remove(query.get());
        currenciesCourseManager.getDs();
        assertThat(query.asList()).hasOnlyElementsOfType(CurrencyCourse.class);
    }

    @Test
    public void t05_testGetDatastore() throws Exception {
        Datastore result = currenciesCourseManager.getDatastore("conn");
        assertThat(result).isInstanceOf(Datastore.class);
    }

    @Test
    public void t04_testGetMorphia() throws Exception {
        Morphia result = currenciesCourseManager.getMorphia();
        assertThat(result).isInstanceOf(Morphia.class);
    }
    @AfterClass
    public static void removeAllCollections() {
        //CurrenciesManager cm = new CurrenciesManager(new MongoClient(),new Morphia());
       // cm.getDs().delete(new TestClassCreator().getCurrency());
        CurrenciesCourseManager ccm = new CurrenciesCourseManager(new MongoClient(), new Morphia());
       ccm.getDatastore(null).delete(ccm.getDs().createQuery(CurrencyCourse.class));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme