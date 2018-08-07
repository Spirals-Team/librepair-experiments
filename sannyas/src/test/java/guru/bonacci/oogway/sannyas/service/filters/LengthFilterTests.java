package guru.bonacci.oogway.sannyas.service.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
public class LengthFilterTests {

	@TestConfiguration
    static class TestContext {
  
        @Bean
        public LengthFilter lengthFilter() {
    		return new LengthFilter();
        }
    }

   	private final static String LEO_SAID = "Study without desire spoils the memory, and it retains nothing that it takes in";

    @Autowired
    LengthFilter lengthFilter;

    @Test
    public void shouldBeLongerThan10Characters() {
	   	ReflectionTestUtils.setField(lengthFilter, "maxLength", 10);
	   	assertThat(lengthFilter.test(LEO_SAID), is(false));
    }

    @Test
    public void shouldNotBeLongerThan100Characters() {
	   	ReflectionTestUtils.setField(lengthFilter, "maxLength", 100);
	   	assertThat(lengthFilter.test(LEO_SAID), is(true));
    }

}
