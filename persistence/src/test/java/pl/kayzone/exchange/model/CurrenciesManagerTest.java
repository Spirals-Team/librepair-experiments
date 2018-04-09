package pl.kayzone.exchange.model;

import com.mongodb.MongoClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import pl.kayzone.exchange.model.entity.Currency;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CurrenciesManagerTest {
    @Mock Datastore ds;
    @Mock Query<Currency> query;
    @Mock Logger LOGG;
    @Mock MongoClient mongo;
    @Mock Morphia morphia;
    CurrenciesManager currenciesManager;
    TestClassCreator tcc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.currenciesManager = Mockito.mock(CurrenciesManager.class);
        this.tcc = new TestClassCreator();
    }

    @Test
    public void t01_testSave() throws Exception {
        Currency c= tcc.getCurrency();
        currenciesManager.save(c);

        verify(currenciesManager).save(c);
    }

    @Test
    public void t02_testSaveNullNotExecute() throws Exception {
        Datastore ds = Mockito.mock(Datastore.class);
        when(currenciesManager.getDs()).thenReturn(ds);

        currenciesManager.save(null);

        verify(currenciesManager,atLeast(1)).save(nullable(Currency.class));
        verify(currenciesManager.getDs(),never()).save(nullable(Currency.class));
    }

    @Test
    public void testFindAll() throws Exception {
        when(currenciesManager.findAll()).thenReturn(Arrays.asList(tcc.getCurrency()));

        List<Currency> result = currenciesManager.findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasOnlyElementsOfType(Currency.class);
        assertThat(result).isInstanceOfAny( List.class);
    }

    @Test
    public void testFind() throws Exception {
        when(currenciesManager.find("id")).thenReturn(tcc.getCurrency());

        Currency result = currenciesManager.find("id");

        assertThat(result).isNotNull();
        assertThat(result).hasFieldOrPropertyWithValue("name","dolar amerykański");
        assertThat(result).hasFieldOrPropertyWithValue("idCode", "USD");
    }

    @Test
    public void testCreateOperations() throws Exception {
        UpdateOperations<Currency> result = currenciesManager.createOperations();
        Assert.assertEquals(null, result);
    }

    @Test
    public void testUpdate() throws Exception {
        UpdateResults result = currenciesManager.update(new Currency("idCode", "name", "urlNbp", "tablesType", Double.valueOf(0)), null);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testGetDatastore() throws Exception {
        Datastore result = currenciesManager.getDatastore("conn");
        Assert.assertEquals(null, result);
    }

    @Test
    public void testGetMorphia() throws Exception {
        Morphia result = currenciesManager.getMorphia();
        Assert.assertEquals(null, result);
    }

    @Test
    public void testRemove() throws Exception {
        currenciesManager.remove(new Currency("idCode", "name", "urlNbp", "tablesType", Double.valueOf(0)));
    }
}
