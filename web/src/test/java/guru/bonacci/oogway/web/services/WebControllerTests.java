package guru.bonacci.oogway.web.services;

import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.web.WebTestApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestApp.class, properties = {
        "security.basic.enabled=false"
}, webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class WebControllerTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	FirstLineSupportService service;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void shouldReceive200OnConsult() throws Exception {
		GemCarrier gem = new GemCarrier("why should I?", "oogway");
		given(service.enquire("tell me the truth")).willReturn(gem);
		
		mvc.perform(get("/consult?q=tell me the truth"))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(gem)));
		}
}
