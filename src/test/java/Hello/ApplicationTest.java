package Hello;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
    private String BUDDY_NAME = "TEST";
    private String BUDDY_NUMBER = "61364136123";

    @Autowired
    private AddressBookRepository aRepo;

    @Autowired
    private BuddyInfoRepository bRepo;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AddBuddyController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
        //assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",String.class)).contains("Hello World");

        String id;
        assertThat(id = this.restTemplate.getForObject("http://localhost:" + port + "/CreateAddressBook", String.class));
        //this.restTemplate.perform(get("/CreateAddressBook")).andDo(print()).andExpect(status().isOk());
        assertThat(aRepo.count()).isGreaterThan(0);


        AddressBook a = aRepo.findAll().iterator().next();

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/AddBuddy?phoneNumber="+BUDDY_NUMBER+"&name="+BUDDY_NAME, String.class));
        //this.restTemplate.perform(get("/AddBuddy?phoneNumber="+BUDDY_NUMBER+"&name="+BUDDY_NAME)).andDo(print()).andExpect(status().isOk());
        BuddyInfo b = bRepo.findByName(BUDDY_NAME).get(0);
        assertThat(b.getName()).isEqualTo(BUDDY_NAME);
        assertThat(b.getPhoneNumber()).isEqualTo(BUDDY_NUMBER);

        assertThat(a.getBuddys().get(0).getName()).isEqualTo(BUDDY_NAME);
        assertThat(a.getBuddys().get(0).getPhoneNumber()).isEqualTo(BUDDY_NUMBER);
        //System.out.println("ALL TESTS PASSED");
    }

}