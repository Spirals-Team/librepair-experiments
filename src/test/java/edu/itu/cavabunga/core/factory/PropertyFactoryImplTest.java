package edu.itu.cavabunga.core.factory;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import edu.itu.cavabunga.core.entity.property.*;
import edu.itu.cavabunga.core.entity.property.Class;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(DataProviderRunner.class)
@SpringBootTest
public class PropertyFactoryImplTest {

    public PropertyFactory propertyFactory;

    @Before
    public void setup(){
        propertyFactory = new PropertyFactoryImpl();
    }

    @DataProvider
    public static Object[][] dataProviderPropertyType(){
        return new Object[][]{
                {PropertyType.Acknowledged,Acknowledged.class},
                {PropertyType.Action,Action.class},
                {PropertyType.Attach,Attach.class},
                {PropertyType.Attendee,Attendee.class},
                {PropertyType.Calscale,Calscale.class},
                {PropertyType.Catagories,Catagories.class},
                {PropertyType.Class,Class.class},
                {PropertyType.Comment,Comment.class},
                {PropertyType.Completed,Completed.class},
                {PropertyType.Contact,Contact.class},
                {PropertyType.Country,Country.class},
                {PropertyType.Created,Created.class},
                {PropertyType.Description,Description.class},
                {PropertyType.Dtend,Dtend.class},
                {PropertyType.Dtstamp,Dtstamp.class},
                {PropertyType.Dtstart,Dtstart.class},
                {PropertyType.Due,Due.class},
                {PropertyType.Duration,Duration.class},
                {PropertyType.Exdate,Exdate.class},
                {PropertyType.Exrule,Exrule.class},
                {PropertyType.Freebusy,Freebusy.class},
                {PropertyType.Geo,Geo.class},
                {PropertyType.Lastmod,Lastmod.class},
                {PropertyType.Location,Location.class},
                {PropertyType.Method,Method.class},
                {PropertyType.Organizer,Organizer.class},
                {PropertyType.Percent,Percent.class},
                {PropertyType.Priority,Priority.class},
                {PropertyType.Prodid,Prodid.class},
                {PropertyType.Rdate,Rdate.class},
                {PropertyType.Recurid,Recurid.class},
                {PropertyType.Related,Related.class},
                {PropertyType.Repeat,Repeat.class},
                {PropertyType.Resources,Resources.class},
                {PropertyType.Rrule,Rrule.class},
                {PropertyType.Rstatus,Rstatus.class},
                {PropertyType.Seq,Seq.class},
                {PropertyType.Status,Status.class},
                {PropertyType.Summary,Summary.class},
                {PropertyType.Transp,Transp.class},
                {PropertyType.Trigger,Trigger.class},
                {PropertyType.Tzid,Tzid.class},
                {PropertyType.Tzname,Tzname.class},
                {PropertyType.Tzoffsetfrom,Tzoffsetfrom.class},
                {PropertyType.Tzoffsetto,Tzoffsetto.class},
                {PropertyType.Tzurl,Tzurl.class},
                {PropertyType.Uid,Uid.class},
                {PropertyType.Url,Url.class},
                {PropertyType.Version,Version.class},
        };
    }

    @Test
    @UseDataProvider("dataProviderPropertyType")
    public void testCreateProperty(PropertyType propertyType, java.lang.Class type){
        assertThat(propertyFactory.createProperty(propertyType), instanceOf(type));
    }
}
