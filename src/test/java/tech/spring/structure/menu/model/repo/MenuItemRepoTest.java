package tech.spring.structure.menu.model.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tech.spring.structure.menu.model.MenuItem;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuItemRepoTest {

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Test
    public void testCreate() {
        long initialiMenuItemRepoCound = menuItemRepo.count();
        MenuItem menuItem = menuItemRepo.create(new MenuItem("gloss", "path"));
        assertNotNull("Unable to create menu item!", menuItem);
        assertEquals("Menu item has incorrect gloss!", "gloss", menuItem.getGloss());
        assertEquals("Menu item has incorrect path!", "path", menuItem.getPath());
        assertEquals("Incorrect number of menu items in repo!", initialiMenuItemRepoCound + 1, menuItemRepo.count());
    }

    @Test
    public void testRead() {
        MenuItem menuItem = menuItemRepo.create(new MenuItem("read", "get"));
        Long id = menuItem.getId();
        Optional<MenuItem> menuItemRead = menuItemRepo.read(id);
        assertTrue("Unable to read menu item!", menuItemRead.isPresent());
        assertEquals("Menu item has incorrect gloss!", "read", menuItem.getGloss());
        assertEquals("Menu item has incorrect path!", "get", menuItem.getPath());
    }

    @Test
    public void testUpdate() {
        MenuItem menuItem = menuItemRepo.create(new MenuItem("gloss", "path"));
        menuItem.setGloss("update");
        menuItem.setPath("put");
        menuItem.setIcon("icon");
        menuItem.setHelp("help");
        menuItem = menuItemRepo.update(menuItem);
        assertEquals("Menu item gloss did not update!", "update", menuItem.getGloss());
        assertEquals("Menu item path did not update!", "put", menuItem.getPath());
        assertEquals("Menu item icon did not update!", "icon", menuItem.getIcon());
        assertEquals("Menu item help did not update!", "help", menuItem.getHelp());
    }

    @Test
    public void testDelete() {
        long initialiMenuItemRepoCound = menuItemRepo.count();
        MenuItem menuItem = menuItemRepo.create(new MenuItem("gloss", "path"));
        assertEquals("Incorrect number of menu items in repo!", initialiMenuItemRepoCound + 1, menuItemRepo.count());
        Long id = menuItem.getId();
        menuItemRepo.delete(menuItem);
        assertFalse("Menu item was not deleted!", menuItemRepo.read(id).isPresent());
        assertEquals("Incorrect number of menu items in repo!", initialiMenuItemRepoCound, menuItemRepo.count());
    }

}
