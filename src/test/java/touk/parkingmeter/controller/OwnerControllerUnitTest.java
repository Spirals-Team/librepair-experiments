package touk.parkingmeter.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import touk.parkingmeter.service.ChargeService;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OwnerControllerUnitTest {
    @Mock
    private ChargeService chargeService;

    @InjectMocks
    private OwnerController ownerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .setControllerAdvice(new ExceptionHandlerAdviceController())
                .build();
    }

    @Test
    public void getDailyIncome() throws  Exception {
        when(chargeService.calculateIncomeFromDay(LocalDate.now())).
                thenReturn(new BigDecimal(0));

        mockMvc.perform(get("/owner/2018-07-30"))
                    .andExpect(status().isOk());
    }
}
