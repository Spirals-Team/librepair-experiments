package YingYingMonster.LetsDo_Phase_I.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.swing.Spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import springfox.documentation.service.MediaTypes;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestWorkSpaceController {

	@Autowired
	private MockMvc mvc;
	
	@Before
	public void setUp() throws Exception {
//		MockitoAnnotations.initMocks(this);
//        InternalResourceViewResolver resolver = 
//        		new InternalResourceViewResolver(); //在test中重新配置视图解析器
//        resolver.setPrefix("src/main/resources/templates/");
//        resolver.setSuffix(".html");
//		mvc=MockMvcBuilders.standaloneSetup(new UserController())
//				.setViewResolvers(resolver).build();
	}

	@Test
	public void test() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/workSpace/id/root/"))
		.andExpect(status().isOk())
		.andExpect(content().string(""));
	}

}
