package guru.bonacci.spectre.sentiment.services;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.spectre.sentiment.SentimentTestApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SentimentTestApp.class, webEnvironment=NONE)
@Ignore
public class SentimentServiceTests {

	@Autowired
	SentimentService service;
	
    @Test
    public void shouldHaveDifferentSentiment() {
    	Integer good = service.findSentiment("wonderful wonder miracle blessing please");
    	Integer bad = service.findSentiment("I am hungry and slightly nervous");
    	Integer ugly = service.findSentiment("terrible fucking shit");
    	
        assertThat(good, is(greaterThan(bad)));
        assertThat(bad, is(greaterThan(ugly)));
    }
}