package asw.inci_manager;

import asw.InciManagerApplication;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InciManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InciManagerapplicationTestLogueado {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private Filter springSecurityFilterChain;
//
//    private MockMvc mvc;
//
//    @Before
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .addFilters(springSecurityFilterChain)
//                .build();
//    }
//
//    @Test
//    public void testHomeLogueado(){
//        //mvc.perform(get("/").with(user("user")));
//    }

}

