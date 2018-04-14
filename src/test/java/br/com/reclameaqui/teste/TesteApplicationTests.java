package br.com.reclameaqui.teste;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TesteApplicationTests {

	@Test
	public void contextLoads() {
	}

}

/*

@RunWith(SpringRunner.class)
@SpringBootConfiguration
public class HelloControllerTest {
 
    private MockMvc mockMvc;
 
    @InjectMocks
    private HelloController helloController;
 
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
    }
 
    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello"));
    }
}

*/