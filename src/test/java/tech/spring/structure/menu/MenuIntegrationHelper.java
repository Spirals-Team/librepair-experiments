package tech.spring.structure.menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import tech.spring.structure.auth.AuthIntegrationHelper;
import tech.spring.structure.menu.model.MenuItem;

public class MenuIntegrationHelper extends AuthIntegrationHelper {

    @Value("classpath:mock/menu.json")
    private Resource menuResource;

    private List<MenuItem> menu;

    @Before
    public void initializeMockScaffold() throws JsonParseException, JsonMappingException, IOException {
        // @formatter:off
        menu = objectMapper.readValue(menuResource.getFile(), new TypeReference<List<MenuItem>>() {});
        // @formatter:on
    }

    protected MenuItem getMockMenuteItem(String gloss, String path) {
        Optional<MenuItem> menuItem = Optional.empty();
        for (MenuItem mi : menu) {
            if (mi.getGloss().equals(gloss) && mi.getPath().equals(path)) {
                menuItem = Optional.of(mi);
                break;
            }
        }
        assertTrue("Unable to find menu item with gloss " + gloss + " and path " + path + "!", menuItem.isPresent());
        return menuItem.get();
    }

    protected List<MenuItem> getMockMenuAsAnonymous() {
        return new ArrayList<MenuItem>() {
            private static final long serialVersionUID = 2909089542504357551L;
            {
                add(getMockMenuteItem("Dashboard", "dashboard"));
                add(getMockMenuteItem("News", "news"));
                add(getMockMenuteItem("Help", "help"));
            }
        };
    }

    protected List<MenuItem> getMockMenuAsUser() {
        return new ArrayList<MenuItem>() {
            private static final long serialVersionUID = 2909089542504357551L;
            {
                add(getMockMenuteItem("Dashboard", "dashboard"));
                add(getMockMenuteItem("News", "news"));
                add(getMockMenuteItem("Help", "help"));
                add(getMockMenuteItem("Settings", "settings"));
                add(getMockMenuteItem("Profile", "profile"));
                add(getMockMenuteItem("Logout", "logout"));
            }
        };
    }

    protected List<MenuItem> getMockMenuAsAdmin() {
        return new ArrayList<MenuItem>() {
            private static final long serialVersionUID = 2909089542504357551L;
            {
                add(getMockMenuteItem("Dashboard", "dashboard"));
                add(getMockMenuteItem("News", "news"));
                add(getMockMenuteItem("Help", "help"));
                add(getMockMenuteItem("Settings", "settings"));
                add(getMockMenuteItem("Profile", "profile"));
                add(getMockMenuteItem("Admin", "admin"));
                add(getMockMenuteItem("Logout", "logout"));
            }
        };
    }

    public static void assertMenu(List<MenuItem> mockMenu, List<MenuItem> menu) {
        assertEquals("Menu did not have the expected number of menu items!", mockMenu.size(), menu.size());
        for (int i = 0; i < mockMenu.size(); i++) {
            MenuItem mockMenuItem = mockMenu.get(i);
            for (int j = 0; j < mockMenu.size(); j++) {
                MenuItem menuItem = menu.get(j);
                if (mockMenuItem.getGloss().equals(menuItem.getGloss()) && mockMenuItem.getPath().equals(menuItem.getPath())) {
                    assertMenuItem(mockMenu.get(i), menu.get(j));
                }
            }
        }
    }

    public static void assertMenuItem(MenuItem mockMenuItem, MenuItem menuItem) {
        assertEquals("Menu item does not have correct gloss!", mockMenuItem.getGloss(), menuItem.getGloss());
        assertEquals("Menu item does not have correct path!", mockMenuItem.getPath(), menuItem.getPath());
        assertEquals("Menu item does not have correct icon!", mockMenuItem.getIcon(), menuItem.getIcon());
        assertEquals("Menu item does not have correct help!", mockMenuItem.getHelp(), menuItem.getHelp());
    }

}
