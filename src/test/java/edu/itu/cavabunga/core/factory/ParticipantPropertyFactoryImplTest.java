package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.ParticipantProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParticipantPropertyFactoryImplTest {
    @Autowired
    ParticipantPropertyFactory participantPropertyFactory;

    @Test
    public void createComponentPropertyTest(){
        assertThat(participantPropertyFactory.create(), instanceOf(ParticipantProperty.class));
    }

}
