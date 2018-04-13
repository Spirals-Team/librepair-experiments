package guru.bonacci.oogway.doorway.ip;

import static guru.bonacci.oogway.doorway.ip.IPologist.LOCAL_IP_1;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.doorway.DoorwayTestApp;
import guru.bonacci.oogway.doorway.ip.IPologist;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DoorwayTestApp.class, webEnvironment = NONE)
@ActiveProfiles("dev") //test the dev bean
public class IPologistTests {

    @Autowired
    IPologist ipologist;

	@MockBean
	HttpServletRequest request;

    @Test
    public void shouldReturnRandomIpWhenLocal() {
    	assertThat(ipologist.checkUp(LOCAL_IP_1), is(not(equalTo(LOCAL_IP_1))));
    }

    @Test
    public void shouldReturnSameIpWhenNotLocal() {
    	String ip = "164.243.120.46";
    	assertThat(ipologist.checkUp(ip), is(equalTo(ip)));
    }
}
