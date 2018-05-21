package edu.itu.cavabunga.core.service;

import edu.itu.cavabunga.core.factory.ComponentFactory;
import edu.itu.cavabunga.core.factory.ParameterFactory;
import edu.itu.cavabunga.core.factory.PropertyFactory;
import edu.itu.cavabunga.core.repository.ComponentRepository;
import edu.itu.cavabunga.core.repository.ParameterRepository;
import edu.itu.cavabunga.core.repository.PropertyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IcalServiceImplTest {

    @MockBean
    private ComponentFactory componentFactory;

    @MockBean
    private ComponentRepository componentRepository;

    @MockBean
    private PropertyFactory propertyFactory;

    @MockBean
    private PropertyRepository propertyRepository;

    @MockBean
    private ParameterFactory parameterFactory;

    @MockBean
    private ParameterRepository parameterRepository;

    private IcalServiceImpl icalService;

    public void setUp() {
        icalService = new IcalServiceImpl(
                componentFactory,
                componentRepository,
                propertyFactory,
                propertyRepository,
                parameterFactory,
                parameterRepository
        );
    }

    @Test
    public void testCreateComponent() {

    }
}
