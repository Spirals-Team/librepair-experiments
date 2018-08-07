package guru.bonacci.oogway.lumberjack.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LogServiceTests {

	@Autowired
	LogService service;

	@Autowired
	LogRepository repository;

	Log logline1, logline2, logline3;

	@BeforeEach
	public void init() {
		repository.deleteAll();
	}

	@Test
	public void shouldInsertAndReturnNrOfLogsLastMinute() {
		logline1 = new Log();
		logline1.setApiKey("123");
		long howMany = service.insert(logline1);
		assertThat(howMany, is(1l));
		
		logline2 = new Log();
		logline2.setApiKey("456");
		howMany = service.insert(logline2);
		assertThat(howMany, is(1l));
		
		logline3 = new Log();
		logline3.setApiKey("123");
		howMany = service.insert(logline3);
		assertThat(howMany, is(2l));

		assertThat(repository.count(), is(3l));
	}
}
