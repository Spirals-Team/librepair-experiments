package ru.neustupov.votingforrestaurants.web.menu;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.votingforrestaurants.TestUtil;
import ru.neustupov.votingforrestaurants.model.Menu;
import ru.neustupov.votingforrestaurants.service.MenuService;
import ru.neustupov.votingforrestaurants.web.AbstractControllerTest;
import ru.neustupov.votingforrestaurants.web.json.JsonUtil;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.votingforrestaurants.MenuTestData.*;
import static ru.neustupov.votingforrestaurants.RestaurantTestData.RUSSIA_ID;

public class AdminMenuRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = AdminMenuRestController.REST_URL + '/';

    @Autowired
    private MenuService menuService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RUSSIA_MENU_ID1)
        .param("restId", "100002"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RUSSIA_MENU1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RUSSIA_MENU_ID1)
        .param("restId", "100002"))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(menuService.getAll(RUSSIA_ID), RUSSIA_MENU2, MENU_TODAYS_WITH_MEALS);
    }

    @Test
    public void testCreate() throws Exception {
        Menu expected = new Menu(getCreated());
        ResultActions action = mockMvc.perform(post(REST_URL)
                .param("restId", "100002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Menu returned = TestUtil.readFromJson(action, Menu.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(menuService.getAll(RUSSIA_ID), RUSSIA_MENU1, RUSSIA_MENU2, MENU_TODAYS_WITH_MEALS, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
        .param("restId", "100002"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RUSSIA_MENU1, RUSSIA_MENU2, MENU_TODAYS_WITH_MEALS)));
    }

    @Test
    public void testUpdate() throws Exception {
        Menu updated = new Menu(getCreated());
        updated.setId(100007);
        updated.setAddDate(Date.valueOf("2017-06-01"));
        mockMvc.perform(put(REST_URL + RUSSIA_MENU_ID1)
                .param("restId", "100002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(menuService.get(100007, RUSSIA_ID), updated);
    }
}
