package guru.bonacci.oogway.doorway.ip;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class IPologistTests {

	@TestConfiguration
    static class TestContext {
  
        @Bean
        public IPologist ipologist() {
    		return new IPologist(new IPerable());
        }
    }

    @Autowired
    IPologist ipologist;

    @Test
    public void shouldReturnRandomIpWhenLocal() {
    	assertThat(ipologist.checkUp("127.0.0.1"), is(not(equalTo("127.0.0.1"))));
    }

    @Test
    public void shouldReturnSameIpWhenNotLocal() {
    	String ip = "164.243.120.46";
    	assertThat(ipologist.checkUp(ip), is(equalTo(ip)));
    }
}
