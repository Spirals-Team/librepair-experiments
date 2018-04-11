package guru.bonacci.oogway.oracle.service.services;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.bonacci.oogway.oracle.service.OracleTestApp;
import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OracleTestApp.class, properties = {
        "security.basic.enabled=false"
}, webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("unit-test")
public class GemControllerTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	GemRepository gemRepo;

	//jackson-datatype-jdk8 converts an empty optional to null instead of an empty json
	//Jdk8Module.configureAbsentsAsNulls does not change this behaviour, it only works on fields
	@Ignore 
	@Test
	public void shouldReturnNullOnConsult() throws Exception {
		given(gemRepo.consultTheOracle("tell me the truth")).willReturn(Optional.empty());

		mvc.perform(get("/gems?q=tell me the truth"))
			.andExpect(status().isOk())
			.andExpect(content().string(isEmptyOrNullString()));
	}

	@Test
	public void shouldReceiveMessageOnQ() throws Exception {
		given(gemRepo.consultTheOracle("tell me the truth", "dummy"))
			.willReturn(Optional.of(new Gem("why should I?", "dumb")));

		mvc.perform(get("/gems?q=tell me the truth&by=dummy"))
			.andExpect(status().isOk())
			.andExpect(content().json("{'saying':'why should I?', 'author':'dumb'}")); 
	}
	
	@Test
	public void shouldReceiveMessageOnQAndBy() throws Exception {
		given(gemRepo.consultTheOracle("tell me the truth", "dummy"))
			.willReturn(Optional.of(new Gem("why should I?", "dumb")));

		mvc.perform(get("/gems?q=tell me the truth&by=dummy"))
			.andExpect(status().isOk())
			.andExpect(content().json("{'saying':'why should I?', 'author':'dumb'}")); 
	}

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void shouldInsertThroughBackdoor() throws Exception {
		mvc.perform(post("/gems/backdoor")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(new GemCarrier("a","b"))))
			.andExpect(status().isOk()); 
	}

	@Test
	public void shouldBlockInvalidInputThroughBackdoor() throws Exception {
		mvc.perform(post("/gems/backdoor")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(new GemCarrier("I cannot be inserted","b"))))
			.andExpect(status().isBadRequest()); 
	}

	@Test
	public void shouldBlockEmptySayingThroughBackdoor() throws Exception {
		mvc.perform(post("/gems/backdoor")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(new GemCarrier("","b"))))
			.andExpect(status().isBadRequest()); 
	}
}
