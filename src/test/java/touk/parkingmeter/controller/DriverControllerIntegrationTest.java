package touk.parkingmeter.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import touk.parkingmeter.ParkingMeterApplication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParkingMeterApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class DriverControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addingSecondTicketWithTheSamePlateShouldReturnConflict() throws Exception {
        String postBody = "{\"plateNumber\": \"www1234\"," +
                "\"driverType\": \"REGULAR\"," +
                "\"currency\": \"PLN\"}";

        mockMvc.perform(post("/driver/startTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/driver/startTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody))
                .andExpect(status().isConflict());
    }

    @Test
    public void afterAddingNewTicketShouldBeAbleToGetIt() throws Exception {
        String postBody = "{\"plateNumber\": \"www123\"," +
                "\"driverType\": \"REGULAR\"," +
                "\"currency\": \"PLN\"}";

        mockMvc.perform(post("/driver/startTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/driver/www123"))
                .andExpect(status().isOk());
    }

    @Test
    public void gettingTicketThatDoNotExistShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/driver/www123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void afterAddingNewTicketShouldBeAbleToEndIt() throws Exception {
        String postBody = "{\"plateNumber\": \"www1235\"," +
                "\"driverType\": \"REGULAR\"," +
                "\"currency\": \"PLN\"}";

        mockMvc.perform(post("/driver/startTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/driver/endTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody))
                .andExpect(status().isOk());
    }
}
