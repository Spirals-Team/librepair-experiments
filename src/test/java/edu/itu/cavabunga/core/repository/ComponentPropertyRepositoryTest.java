package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.ComponentProperty;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.component.ComponentType;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.factory.ComponentFactory;
import edu.itu.cavabunga.core.factory.ComponentPropertyFactory;
import edu.itu.cavabunga.core.factory.ParticipantFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ComponentPropertyRepositoryTest {
    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    ParticipantFactory participantFactory;

    @Autowired
    ComponentRepository componentRepository;

    @Autowired
    ComponentFactory componentFactory;

    @Autowired
    ComponentPropertyRepository componentPropertyRepository;

    @Autowired
    ComponentPropertyFactory componentPropertyFactory;

    private Component testComponent;

    @Before
    public void setUp(){
        Participant testUser = participantFactory.createParticipant(ParticipantType.User);
        testUser.setUserName("testuser");
        participantRepository.save(testUser);

        Component testComponent = componentFactory.createComponent(ComponentType.Calendar);
        testComponent.setOwner(testUser);
        componentRepository.save(testComponent);
        this.testComponent = componentRepository.findByOwner(participantRepository.findByUserName("testuser")).get(0);

        ComponentProperty testComponentProperty = componentPropertyFactory.createComponentProperty();
        testComponentProperty.setComponent(this.testComponent);
        componentPropertyRepository.save(testComponentProperty);
    }

    @Test
    public void findByComponentTest(){
        assertThat(componentPropertyRepository.findByComponent(this.testComponent), (instanceOf(ComponentProperty.class)));
    }
}
