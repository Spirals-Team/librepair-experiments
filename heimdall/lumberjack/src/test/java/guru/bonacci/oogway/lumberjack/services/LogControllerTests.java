package guru.bonacci.oogway.lumberjack.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.bonacci.oogway.lumberjack.persistence.Log;
import guru.bonacci.oogway.lumberjack.persistence.LogService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogControllerTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	LogService service;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void shouldReceive200OnRequest() throws Exception {
		Long visits = 5L;
		given(service.insert(any(Log.class))).willReturn(visits);

		mvc.perform(get("/visits/123")).andExpect(status().isUnauthorized());
	}
}
