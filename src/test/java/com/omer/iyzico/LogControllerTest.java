package com.omer.iyzico;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.omer.iyzico.model.Log;
import com.omer.iyzico.service.LogService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LogService logService;

	@Test
	public void get_logs_succes() throws Exception {

		Log log1 = new Log();
		log1.setCardNumber("111");
		log1.setProcessId("001");
		log1.setCreateDate(Calendar.getInstance().getTime());
		when(logService.findAll()).thenReturn(Arrays.asList(log1));

		mockMvc.perform(get("/logs")).andExpect(status().isOk()).andExpect(jsonPath("$..cardNumber").value("111"));
	}

	@Test
	public void get_single_log_succes() throws Exception {

		Log log1 = new Log();
		log1.setCardNumber("111");
		log1.setProcessId("001");
		log1.setId(1);
		log1.setCreateDate(Calendar.getInstance().getTime());
		when(logService.findById(1)).thenReturn(log1);

		mockMvc.perform(get("/logs/1")).andExpect(status().isOk()).andExpect(jsonPath("$..id").value(1));
	}
}