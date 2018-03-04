package edu.itu.cavabunga.core.factory;


import edu.itu.cavabunga.core.entity.ComponentProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ComponentPropertyFactoryImplTest {
    @Autowired
    ComponentPropertyFactory componentPropertyFactory;

    @Test
    public void createComponentPropertyTest(){
        assertThat(componentPropertyFactory.createComponentProperty(), instanceOf(ComponentProperty.class));
    }

}
