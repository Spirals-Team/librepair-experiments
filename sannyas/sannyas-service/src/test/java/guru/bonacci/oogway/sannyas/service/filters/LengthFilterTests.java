package guru.bonacci.oogway.sannyas.service.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import guru.bonacci.oogway.sannyas.service.SannyasTestApp;
import guru.bonacci.oogway.sannyas.service.filters.LengthFilter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SannyasTestApp.class, webEnvironment=NONE)
public class LengthFilterTests {

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
