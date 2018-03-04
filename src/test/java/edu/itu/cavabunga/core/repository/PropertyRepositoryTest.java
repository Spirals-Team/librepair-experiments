package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.ComponentType;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.entity.property.Dtstart;
import edu.itu.cavabunga.core.entity.property.Location;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.core.factory.ComponentFactory;
import edu.itu.cavabunga.core.factory.ParticipantFactory;
import edu.itu.cavabunga.core.factory.PropertyFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PropertyRepositoryTest {
    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    ParticipantFactory participantFactory;

    @Autowired
    ComponentRepository componentRepository;

    @Autowired
    ComponentFactory componentFactory;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    PropertyFactory propertyFactory;

    private Component testComponent;

    @Before
    public void setUp(){
        Participant testUser = participantFactory.createParticipant(ParticipantType.User);
        testUser.setUserName("testuser");
        participantRepository.save(testUser);

        Component testComponent = componentFactory.createComponent(ComponentType.Event);
        testComponent.setOwner(testUser);
        componentRepository.save(testComponent);
        this.testComponent = componentRepository.findByOwner(testUser).get(0);

        Property testProperty1 = propertyFactory.createProperty(PropertyType.Dtstart);
        testProperty1.setValue("19980118T230000");
        testProperty1.setComponent(testComponent);
        propertyRepository.save(testProperty1);

        Property testProperty2 = propertyFactory.createProperty(PropertyType.Location);
        testProperty2.setValue("ISTANBUL/TURKEY");
        testProperty2.setComponent(testComponent);
        propertyRepository.save(testProperty2);
    }

    @Test
    public void findByComponentTest(){
        List<Property> propertyList = propertyRepository.findByComponent(this.testComponent);
        assertThat(propertyList, contains(instanceOf(Dtstart.class), instanceOf(Location.class)));
    }

    @Test
    public void findByTypeTest(){
        List<Property> propertyList = propertyRepository.findByType(PropertyType.Dtstart.toString());
        assertThat(propertyList, contains(instanceOf(Dtstart.class)));

        List<Property> propertyList2 = propertyRepository.findByType(PropertyType.Location.toString());
        assertThat(propertyList2, contains(instanceOf(Location.class)));
    }

    @Test
    public void findByComponentAndTypeTest(){
        List<Property> propertyList = propertyRepository.findByComponentAndType(this.testComponent, PropertyType.Dtstart.toString());
        assertThat(propertyList, contains(instanceOf(Dtstart.class)));

        List<Property> propertyList2 = propertyRepository.findByComponentAndType(this.testComponent, PropertyType.Location.toString());
        assertThat(propertyList2, contains(instanceOf(Location.class)));
    }

    @Test
    public void countPropertiesByIdTest(){
        List<Property> propertyList = propertyRepository.findByComponent(this.testComponent);
        for(Property t: propertyList){
            assertEquals(Long.valueOf("1"), propertyRepository.countPropertiesById(t.getId()));
        }
    }

    @Test
    public void countPropertyByIdAndParentIdTest(){
        List<Property> propertyList = propertyRepository.findByComponent(this.testComponent);
        for(Property t : propertyList){
            assertEquals(Long.valueOf("1"), propertyRepository.countPropertyByIdAndParentId(t.getId(), this.testComponent.getId()));
        }
    }

    @Test
    public void countPropertyByParentIdTest(){
        assertEquals(Long.valueOf("2"), propertyRepository.countPropertyByParentId(this.testComponent.getId()));
    }
}
