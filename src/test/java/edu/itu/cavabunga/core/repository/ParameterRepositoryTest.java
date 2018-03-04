package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.ComponentType;
import edu.itu.cavabunga.core.entity.parameter.Encoding;
import edu.itu.cavabunga.core.entity.parameter.Language;
import edu.itu.cavabunga.core.entity.parameter.ParameterType;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.core.factory.ComponentFactory;
import edu.itu.cavabunga.core.factory.ParameterFactory;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ParameterRepositoryTest {
    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    ComponentRepository componentRepository;

    @Autowired
    ParticipantFactory participantFactory;

    @Autowired
    ComponentFactory componentFactory;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    PropertyFactory propertyFactory;

    @Autowired
    ParameterRepository parameterRepository;

    @Autowired
    ParameterFactory parameterFactory;

    private Property testParentProperty;

    @Before
    public void setUp(){
        Participant testUser = participantFactory.createParticipant(ParticipantType.User);
        testUser.setUserName("testuser");
        participantRepository.save(testUser);

        Component testevent = componentFactory.createComponent(ComponentType.Event);
        testevent.setOwner(testUser);
        componentRepository.save(testevent);

        Property testproperty = propertyFactory.createProperty(PropertyType.Attach);
        testproperty.setValue("TESTDATABASE64_VEVTVF9EQVRB");
        testproperty.setComponent(testevent);
        propertyRepository.save(testproperty);
        this.testParentProperty = propertyRepository.findByComponent(componentRepository.findByOwner(participantRepository.findByUserName("testuser")).get(0)).get(0);

        Parameter testparameter1 = parameterFactory.createParameter(ParameterType.Encoding);
        testparameter1.setValue("UTF-8");
        testparameter1.setProperty(this.testParentProperty);
        parameterRepository.save(testparameter1);

        Parameter testparameter2 = parameterFactory.createParameter(ParameterType.Language);
        testparameter2.setValue("ENGLISH");
        testparameter2.setProperty(this.testParentProperty);
        parameterRepository.save(testparameter2);
    }

    @Test
    public void findByPropertyTest(){
        assertThat(parameterRepository.findByProperty(this.testParentProperty), contains(instanceOf(Encoding.class), instanceOf(Language.class)));
    }

    @Test
    public void findByTypeTest(){
        assertThat(parameterRepository.findByType(ParameterType.Encoding.toString()), contains(instanceOf(Encoding.class)));
        assertThat(parameterRepository.findByType(ParameterType.Language.toString()), contains(instanceOf(Language.class)));
    }

    @Test
    public void findByPropertyAndTypeTest(){
        assertThat(parameterRepository.findByPropertyAndType(this.testParentProperty,ParameterType.Encoding.toString()), contains(instanceOf(Encoding.class)));
        assertThat(parameterRepository.findByPropertyAndType(this.testParentProperty,ParameterType.Language.toString()), contains(instanceOf(Language.class)));
    }

    @Test
    public void countParametersById(){
        List<Parameter> parameterList = parameterRepository.findByProperty(this.testParentProperty);
        for(Parameter t : parameterList){
            assertEquals(Long.valueOf("1"), parameterRepository.countParametersById(t.getId()));
        }
    }

    @Test
    public void countParameterByIdAndParentIdTest(){
        List<Parameter> parameterList = parameterRepository.findByProperty(this.testParentProperty);
        for(Parameter t : parameterList){
            assertEquals(Long.valueOf("1"), parameterRepository.countParameterByIdAndParentId(t.getId(), this.testParentProperty.getId()));
        }
    }

    @Test
    public void countParameterByParentIdTest(){
        List<Parameter> parameterList = parameterRepository.findByProperty(this.testParentProperty);
        assertEquals(Long.valueOf("2"), parameterRepository.countParameterByParentId(this.testParentProperty.getId()));
    }

}
