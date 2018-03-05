package com.omer.iyzico;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omer.iyzico.model.Request;
import com.omer.iyzico.service.LogService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void payment_succes() throws Exception {

		Request request = new Request();
		request.setCardNumber("5526080000000006");
		request.setProcessId("-1");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("02/12/2018"));

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(request);

		mockMvc.perform(post("/payment").content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("Ödeme Alındı"));
	}

	@Test
	public void getBinNumber_success() throws Exception {

		mockMvc.perform(get("/getBinNumber").param("binNumber", "554960"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("success"));
	}

}