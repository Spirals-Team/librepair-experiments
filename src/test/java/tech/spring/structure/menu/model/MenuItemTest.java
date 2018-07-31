package tech.spring.structure.menu.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MenuItemTest {

    @Test
    public void testNewMenuItem() {
        MenuItem menuItem = new MenuItem();
        assertNotNull("Could not create new menu item!", menuItem);
    }

    @Test
    public void testSetGloss() {
        MenuItem menuItem = new MenuItem();
        menuItem.setGloss("gloss");
        assertEquals("Could not set menu item gloss!", "gloss", menuItem.getGloss());
    }

    @Test
    public void testSetPath() {
        MenuItem menuItem = new MenuItem();
        menuItem.setPath("path");
        assertEquals("Could not set menu item path!", "path", menuItem.getPath());
    }

    @Test
    public void testSetIcon() {
        MenuItem menuItem = new MenuItem();
        menuItem.setIcon("icon");
        assertEquals("Could not set menu item icon!", "icon", menuItem.getIcon());
    }

    @Test
    public void testSetHelp() {
        MenuItem menuItem = new MenuItem();
        menuItem.setHelp("help");
        assertEquals("Could not set menu item help!", "help", menuItem.getHelp());
    }

}
