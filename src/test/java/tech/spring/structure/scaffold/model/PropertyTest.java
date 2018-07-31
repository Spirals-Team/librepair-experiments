package tech.spring.structure.scaffold.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PropertyTest {

    @Test
    public void testNewPropertyOfNameAndClazz() {
        Property property = Property.of("property", "String");
        assertNotNull("Unable to create property!", property);
        assertEquals("Property did not have correct name!", "property", property.getName());
        assertEquals("Property did not have correct clazz!", "String", property.getClazz());
    }

    @Test
    public void testSetName() {
        Property property = Property.of("property", "String");
        property.setName("name");
        assertEquals("Property did not set name!", "name", property.getName());
    }

    @Test
    public void testSetClazz() {
        Property property = Property.of("property", "String");
        property.setClazz("Integer");
        assertEquals("Property did not set clazz!", "Integer", property.getClazz());
    }

    @Test
    public void testSetGloss() {
        Property property = Property.of("property", "String");
        property.setGloss("gloss");
        assertEquals("Property did not set gloss!", "gloss", property.getGloss());
    }

    @Test
    public void testSetType() {
        Property property = Property.of("property", "String");
        property.setType("type");
        assertEquals("Property did not set type!", "type", property.getType());
    }

    @Test
    public void testSetHelp() {
        Property property = Property.of("property", "String");
        property.setHelp("help");
        assertEquals("Property did not set help!", "help", property.getHelp());
    }

    @Test
    public void testSetAutocomplete() {
        Property property = Property.of("property", "String");
        property.setAutocomplete("autocomplete");
        assertEquals("Property did not set autocomplete!", "autocomplete", property.getAutocomplete());
    }

    @Test
    public void testSetAutofocus() {
        Property property = Property.of("property", "String");
        property.setAutofocus(true);
        assertEquals("Property did not set autofocus!", true, property.isAutofocus());
    }

    @Test
    public void testSetHidden() {
        Property property = Property.of("property", "String");
        property.setHidden(true);
        assertEquals("Property did not set hidden!", true, property.isHidden());
    }

    @Test
    public void testSetDisabled() {
        Property property = Property.of("property", "String");
        property.setDisabled(true);
        assertEquals("Property did not set disabled!", true, property.isDisabled());
    }

    @Test
    public void testSetOptions() {
        Property property = Property.of("property", "String");
        List<Object> options = new ArrayList<Object>();
        property.setOptions(options);
        assertNotNull("Property did not set options!", property.getOptions());
    }

    @Test
    public void testSetValidations() {
        Property property = Property.of("property", "String");
        List<Validation> validations = new ArrayList<Validation>();
        property.setValidations(validations);
        assertNotNull("Property did not set validations!", property.getValidations());
    }

    @Test
    public void testAddValidation() {
        Property property = Property.of("property", "String");
        property.addValidation(new Validation("name", "message"));
        assertEquals("Property validations has incorrect size!", 1, property.getValidations().size());
        assertEquals("Property validations did not have validation name!", "name", property.getValidations().get(0).getName());
        assertEquals("Property validations did not have validation message!", "message", property.getValidations().get(0).getMessage());
    }

    @Test
    public void testAddValidations() {
        Property property = Property.of("property", "String");
        List<Validation> validations = new ArrayList<Validation>();
        property.addValidations(validations);
        assertNotNull("Property did not add validations!", property.getValidations());
    }

}
