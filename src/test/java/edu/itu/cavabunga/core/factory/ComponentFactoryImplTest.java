package edu.itu.cavabunga.core.factory;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import edu.itu.cavabunga.core.entity.component.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(DataProviderRunner.class)
@SpringBootTest
public class ComponentFactoryImplTest {

    public ComponentFactory componentFactory;

    @Before
    public void setup() {
        componentFactory = new ComponentFactoryImpl();
    }

    @DataProvider
    public static Object[][] dataProviderComponentType() {
        return new Object[][] {
                { ComponentType.Alarm, Alarm.class},
                { ComponentType.Calendar, Calendar.class},
                { ComponentType.Daylight, Daylight.class},
                { ComponentType.Event, Event.class},
                { ComponentType.Freebusy, Freebusy.class},
                { ComponentType.Journal, Journal.class},
                { ComponentType.Standard, Standard.class},
                { ComponentType.Timezone, Timezone.class},
                { ComponentType.Todo, Todo.class},
        };
    }

    @Test
    @UseDataProvider("dataProviderComponentType")
    public void testCreateComponent(ComponentType componentType, Class type) {
        assertThat(componentFactory.createComponent(componentType), instanceOf(type));
    }

}