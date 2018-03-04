package edu.itu.cavabunga.core.factory;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import edu.itu.cavabunga.core.entity.participant.Group;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.entity.participant.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(DataProviderRunner.class)
@SpringBootTest
public class ParticipantFactoryImplTest {

    public ParticipantFactory participantFactory;

    @Before
    public void setup(){
        participantFactory = new ParticipantFactoryImpl();
    }

    @DataProvider
    public static Object[][] dataProviderParticipantType(){
        return new Object[][]{
                {ParticipantType.User, User.class},
                {ParticipantType.Group, Group.class}
        };
    }

    @Test
    @UseDataProvider("dataProviderParticipantType")
    public void testCreateProperty(ParticipantType participantType, Class type) {
        assertThat(participantFactory.createParticipant(participantType), instanceOf(type));
    }
}
