package ru.neustupov.votingforrestaurants.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.votingforrestaurants.TestUtil;
import ru.neustupov.votingforrestaurants.model.Meal;
import ru.neustupov.votingforrestaurants.service.MealService;
import ru.neustupov.votingforrestaurants.web.AbstractControllerTest;
import ru.neustupov.votingforrestaurants.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.votingforrestaurants.MealTestData.*;
import static ru.neustupov.votingforrestaurants.MenuTestData.RUSSIA_MENU_ID1;

public class AdminMealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMealRestController.REST_URL + '/';

    @Autowired
    private MealService mealService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + APPLE_ID)
                .param("menuId", "100007"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(APPLE));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + APPLE_ID)
                .param("menuId", "100007"))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(RUSSIA_MENU_ID1), BOTTLE_OF_WATER);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(APPLE);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + APPLE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .param("mealId", "100014")
                .param("menuId", "100007"))
                .andExpect(status().isOk());

        assertMatch(mealService.get(APPLE_ID, RUSSIA_MENU_ID1), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal("Potatoes", 250);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .param("menuId", "100007")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(action, Meal.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(mealService.getAll(RUSSIA_MENU_ID1), APPLE, BOTTLE_OF_WATER, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .param("menuId", "100007"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(APPLE, BOTTLE_OF_WATER)));
    }
}
