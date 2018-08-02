package touk.parkingmeter.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import touk.parkingmeter.dto.TicketDto;
import touk.parkingmeter.exception.ResourceNotFoundException;
import touk.parkingmeter.service.TicketService;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
public class DriverControllerUnitTest {
    @Mock
    private TicketService  ticketService;

    @InjectMocks
    private DriverController driverController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(driverController)
                .setControllerAdvice(new ExceptionHandlerAdviceController())
                .build();
    }

    @Test
    public void startTicket() throws Exception {
        String postBody = "{\"plateNumber\": \"www123\"," +
                "\"driverType\": \"REGULAR\"," +
                "\"currency\": \"PLN\"}";
        TicketDto ticketDto = mock(TicketDto.class);

        when(ticketService.startTicket(ticketDto)).thenReturn(ticketDto);

        mockMvc.perform(post("/driver/startTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody))
                .andExpect(status().isCreated());
    }


    @Test
    public void endTicket() throws Exception {
        String postBody = "{\"plateNumber\": \"www123\"}";
        TicketDto ticketDto = new TicketDto();

        when(ticketService.endTicket("www123")).thenReturn(ticketDto);

        mockMvc.perform(post("/driver/endTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody))
               .andExpect(status().isOk());
    }

    @Test
    public void endTicketThatDontExist() throws Exception {
        String postBody = "{\"plateNumber\": \"www123\"}";
        TicketDto ticketDto = new TicketDto();

        when(ticketService.endTicket("www123")).
                thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(post("/driver/endTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkTicket() throws Exception {
        TicketDto ticketDto = new TicketDto();

        when(ticketService.checkTicket("www123")).thenReturn(ticketDto);

        mockMvc.perform(get("/driver/www123"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkTicketThatDontExist() throws Exception {

        when(ticketService.checkTicket("www123")).
                thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/driver/www123"))
                .andExpect(status().isNotFound());
    }
}