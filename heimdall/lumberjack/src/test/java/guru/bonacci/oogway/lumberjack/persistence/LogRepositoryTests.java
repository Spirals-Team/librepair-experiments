package guru.bonacci.oogway.lumberjack.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LogRepositoryTests {

	@Autowired
	LogRepository repository;

	Log logline1, logline2;

	@BeforeEach
	public void init() {
		repository.deleteAll();
		
		logline1 = new Log();
		logline1.setMoment(Instant.now());
		logline1.setApiKey("123");
		repository.save(logline1);
		
		logline2 = new Log();
		logline2.setMoment(Instant.now());
		logline2.setApiKey("456");
		repository.save(logline2);
	}

	@Test
	public void shouldFindLogsByMomentRange() {
		Instant now = Instant.now();
		List<Log> found = repository.findByMomentBetween(now.minusSeconds(10), now.plusSeconds(10));
		assertThat(found.size(), is(2));
	}

	@Test
	public void shouldCountLogsByMomentRange() {
		Instant now = Instant.now();
		long nrFound = repository.countByMomentBetween(now.minusSeconds(10), now.plusSeconds(10));
		assertThat(nrFound, is(2L));
	}
	
	@Test
	public void shouldFindLogByKeyAndMomentRange() {
		Instant now = Instant.now();
		List<Log> found = repository.findByApiKeyAndMomentBetween(logline1.getApiKey(), now.minusSeconds(10), now.plusSeconds(10));
		assertThat(found.size(), is(1));
	}

	@Test
	public void shouldCountLogByKeyAndMomentRange() {
		Instant now = Instant.now();
		long nrFound = repository.countByApiKeyAndMomentBetween(logline2.getApiKey(), now.minusSeconds(10), now.plusSeconds(10));
		assertThat(nrFound, is(1L));
	}

}
