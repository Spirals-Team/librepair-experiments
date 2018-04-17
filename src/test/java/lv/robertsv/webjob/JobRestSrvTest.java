package lv.robertsv.webjob;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import lv.robertsv.webjob.domain.JobEntity;
import lv.robertsv.webjob.dto.Job;
import lv.robertsv.webjob.repository.ProductRepository;
import lv.robertsv.webjob.rest.JobRestSrv;
import lv.robertsv.webjob.rest.RestSrvResponse;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WebJobsApplication.class, WebJobsWebSocketConfig.class, WebJobsJpaConfig.class, WebJobsSchedulerConfig.class })
@WebMvcTest(JobRestSrv.class)
public class JobRestSrvTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProductRepository productRepository;
	
	@Test
	public void getAllJobs() throws Exception {
		
		List<JobEntity> jobs = ImmutableList.<JobEntity>builder().add(new JobEntity(1L, "/path", "*/5 * * * * ?")).add(new JobEntity(1L, "/path", "*/5 * * * * ?")).build(); 
		
		Mockito.when(productRepository.findAll()).thenReturn(jobs);
		
		mvc.perform(MockMvcRequestBuilders.get("/job/all").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(toJson(jobs)));
	}
	
	@Test
	public void addJob() throws Exception {
		Mockito.when(productRepository.save(Matchers.<JobEntity>anyObject())).thenReturn(new JobEntity(1L, "/path", "*/5 * * * *"));
		
		mvc.perform(MockMvcRequestBuilders.post("/job/add").content(toJson(new Job(null, "/path", "*/5 * * * *"))).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().json(toJson(RestSrvResponse.OK)));
		
	}
	
	@Test
	public void removeJob() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/job/delete/1")).andExpect(status().isOk())
		.andExpect(content().json(toJson(RestSrvResponse.OK)));
	}

	private String toJson(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
	
}