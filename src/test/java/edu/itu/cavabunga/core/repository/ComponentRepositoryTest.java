package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.component.*;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.factory.ComponentFactory;
import edu.itu.cavabunga.core.factory.ParticipantFactory;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static edu.itu.cavabunga.core.entity.component.ComponentType.Calendar;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ComponentRepositoryTest {
    @Autowired
    ParticipantFactory participantFactory;

    @Autowired
    ComponentFactory componentFactory;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    ComponentRepository componentRepository;

    @Before
    public void setUp(){
        Participant testUser = participantFactory.createParticipant(ParticipantType.User);
        testUser.setUserName("testuser");

        Component calendar = componentFactory.createComponent(Calendar);
            calendar.setOwner(testUser);
            calendar.setParent(null);
            componentRepository.save(calendar);

        Component event = componentFactory.createComponent(ComponentType.Event);
            event.setOwner(testUser);
            event.setParent(calendar);
            componentRepository.save(event);

        Component journal = componentFactory.createComponent(ComponentType.Journal);
            journal.setOwner(testUser);
            journal.setParent(calendar);
            componentRepository.save(journal);


        Component timezone = componentFactory.createComponent(ComponentType.Timezone);
            timezone.setOwner(testUser);
            timezone.setParent(event);
            componentRepository.save(timezone);

        Component todo = componentFactory.createComponent(ComponentType.Todo);
            todo.setOwner(testUser);
            todo.setParent(calendar);
            componentRepository.save(todo);

        Component alarm = componentFactory.createComponent(ComponentType.Alarm);
            alarm.setOwner(testUser);
            alarm.setParent(todo);
            componentRepository.save(alarm);

        Component daylight = componentFactory.createComponent(ComponentType.Daylight);
            daylight.setOwner(testUser);
            daylight.setParent(calendar);
            componentRepository.save(daylight);

        Component freebusy = componentFactory.createComponent(ComponentType.Freebusy);
            freebusy.setOwner(testUser);
            freebusy.setParent(calendar);
            componentRepository.save(freebusy);

        Component standard = componentFactory.createComponent(ComponentType.Standard);
            standard.setOwner(testUser);
            standard.setParent(calendar);
            componentRepository.save(standard);

    }

    @Test
    public void findByOwnerTest(){
        Participant testUser = participantRepository.findByUserName("testuser");
        List<Component> componentList = componentRepository.findByOwner(testUser);

        assertThat(componentList, containsInAnyOrder(
                instanceOf(edu.itu.cavabunga.core.entity.component.Calendar.class),
                instanceOf(edu.itu.cavabunga.core.entity.component.Alarm.class),
                instanceOf(Daylight.class),
                instanceOf(Event.class),
                instanceOf(Freebusy.class),
                instanceOf(Journal.class),
                instanceOf(Standard.class),
                instanceOf(Timezone.class),
                instanceOf(Todo.class))
                  );
        assertThat(componentList, containsInAnyOrder(hasProperty("owner", Matchers.is(testUser)),
                                            hasProperty("owner", Matchers.is(testUser)),
                                            hasProperty("owner", Matchers.is(testUser)),
                                            hasProperty("owner", Matchers.is(testUser)),
                                            hasProperty("owner", Matchers.is(testUser)),
                                            hasProperty("owner", Matchers.is(testUser)),
                                            hasProperty("owner", Matchers.is(testUser)),
                                            hasProperty("owner", Matchers.is(testUser)),
                                            hasProperty("owner", Matchers.is(testUser))
                                            )
                );
    }

    @Test
    public void findByParentTest(){
        Participant testUser = participantRepository.findByUserName("testuser");
        List<Component> componentList = componentRepository.findByOwner(testUser);
        Component parentCalendar = componentFactory.createComponent(ComponentType.Calendar);
        for(Component t: componentList){
            if(t instanceof Calendar){
                parentCalendar = t;
                break;
            }
        }
        List<Component> childComponentList = componentRepository.findByParent(parentCalendar);

        //All Components
        assertThat(componentList, containsInAnyOrder(
                instanceOf(edu.itu.cavabunga.core.entity.component.Calendar.class),
                instanceOf(edu.itu.cavabunga.core.entity.component.Alarm.class),
                instanceOf(Daylight.class),
                instanceOf(Event.class),
                instanceOf(Freebusy.class),
                instanceOf(Journal.class),
                instanceOf(Standard.class),
                instanceOf(Timezone.class),
                instanceOf(Todo.class))
        );

        assertThat(componentList, containsInAnyOrder(
                hasProperty("parent", nullValue()),
                hasProperty("parent", instanceOf(parentCalendar.getClass())),
                hasProperty("parent", instanceOf(parentCalendar.getClass())),
                hasProperty("parent", instanceOf(parentCalendar.getClass())),
                hasProperty("parent", instanceOf(parentCalendar.getClass())),
                hasProperty("parent", instanceOf(Event.class)),
                hasProperty("parent", instanceOf(Todo.class)),
                hasProperty("parent", instanceOf(parentCalendar.getClass())),
                hasProperty("parent", instanceOf(parentCalendar.getClass()))
                )
        );

        //Child of calendar
        assertThat(childComponentList, containsInAnyOrder(
                instanceOf(Daylight.class),
                instanceOf(Event.class),
                instanceOf(Freebusy.class),
                instanceOf(Journal.class),
                instanceOf(Standard.class),
                instanceOf(Todo.class))
        );


        assertThat(childComponentList, containsInAnyOrder(
                hasProperty("parent", instanceOf(parentCalendar.getClass())),
                hasProperty("parent", instanceOf(parentCalendar.getClass())),
                hasProperty("parent", instanceOf(parentCalendar.getClass())),
                hasProperty("parent", instanceOf(parentCalendar.getClass())),
                hasProperty("parent", instanceOf(parentCalendar.getClass())),
                hasProperty("parent", instanceOf(parentCalendar.getClass()))
                )
        );

        assertEquals(null, parentCalendar.getParent());

    }

    @Test
    public void findByParentAndType(){
        Participant testUser = participantRepository.findByUserName("testuser");
        List<Component> componentList = componentRepository.findByOwner(testUser);
        Component parentCalendar = componentFactory.createComponent(ComponentType.Calendar);
        for(Component t: componentList){
            if(t instanceof Calendar){
                parentCalendar = t;
                break;
            }
        }
        //!! PROBLEM:
        //      repository doesnt return Components with NULL parent ??
        //List<Component> test = componentRepository.findByParentAndType(null,ComponentType.Calendar.toString());
        //assertThat(componentRepository.findByParentAndType(null,ComponentType.Calendar.toString()), contains(instanceOf(edu.itu.cavabunga.core.entity.component.Calendar.class)));
        assertThat(componentRepository.findByParentAndType(parentCalendar,ComponentType.Event.toString()), contains(instanceOf(Event.class)));
        assertThat(componentRepository.findByParentAndType(parentCalendar,ComponentType.Daylight.toString()), contains(instanceOf(Daylight.class)));
        assertThat(componentRepository.findByParentAndType(parentCalendar,ComponentType.Freebusy.toString()), contains(instanceOf(Freebusy.class)));
        assertThat(componentRepository.findByParentAndType(parentCalendar,ComponentType.Journal.toString()), contains(instanceOf(Journal.class)));
        assertThat(componentRepository.findByParentAndType(parentCalendar,ComponentType.Todo.toString()), contains(instanceOf(Todo.class)));
        assertThat(componentRepository.findByParentAndType(parentCalendar,ComponentType.Standard.toString()), contains(instanceOf(Standard.class)));
    }

    @Test
    public void findByTypeTest(){
        assertThat(componentRepository.findByType(ComponentType.Calendar.toString()),contains(instanceOf(edu.itu.cavabunga.core.entity.component.Calendar.class)));
        assertThat(componentRepository.findByType(ComponentType.Alarm.toString()),contains(instanceOf(Alarm.class)));
        assertThat(componentRepository.findByType(ComponentType.Daylight.toString()),contains(instanceOf(Daylight.class)));
        assertThat(componentRepository.findByType(ComponentType.Event.toString()),contains(instanceOf(Event.class)));
        assertThat(componentRepository.findByType(ComponentType.Freebusy.toString()),contains(instanceOf(Freebusy.class)));
        assertThat(componentRepository.findByType(ComponentType.Journal.toString()),contains(instanceOf(Journal.class)));
        assertThat(componentRepository.findByType(ComponentType.Standard.toString()),contains(instanceOf(Standard.class)));
        assertThat(componentRepository.findByType(ComponentType.Timezone.toString()),contains(instanceOf(Timezone.class)));
        assertThat(componentRepository.findByType(ComponentType.Todo.toString()),contains(instanceOf(Todo.class)));
    }
}
