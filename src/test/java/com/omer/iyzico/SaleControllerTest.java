package com.omer.iyzico;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.omer.iyzico.model.Sale;
import com.omer.iyzico.service.SaleService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SaleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SaleService saleService;

	@Test
	public void get_sales_succes() throws Exception {

		Sale sale = new Sale();
		sale.setId(1);
		sale.setPrice(new BigDecimal("1.0"));
		sale.setProcessId("001");
		sale.setResult("SUCCESS");
		when(saleService.findAll()).thenReturn(Arrays.asList(sale));

		mockMvc.perform(get("/sales")).andExpect(status().isOk()).andExpect(jsonPath("$..result").value("SUCCESS"));
	}

	@Test
	public void get_single_sale_succes() throws Exception {

		Sale sale = new Sale();
		sale.setId(1);
		sale.setPrice(new BigDecimal("1.0"));
		sale.setProcessId("001");
		sale.setResult("SUCCESS");
		when(saleService.findById(1)).thenReturn(sale);

		mockMvc.perform(get("/sales/1")).andExpect(status().isOk()).andExpect(jsonPath("$..id").value(1));
	}
}