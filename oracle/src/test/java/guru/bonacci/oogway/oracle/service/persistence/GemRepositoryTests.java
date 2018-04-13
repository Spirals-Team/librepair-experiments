package guru.bonacci.oogway.oracle.service.persistence;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "spring.sleuth.enabled=false",
        "spring.zipkin.enabled=false"
}, webEnvironment = NONE)
@Ignore
public class GemRepositoryTests {
	
	@Autowired
	GemRepository repo;
	
	@Before
	public void setup() {
		repo.deleteAll();
	}

	@Ignore
	@Test
	public void shouldSaveAllFields1() {
		String said = "the said";
		String author = "the by";
		Gem input = new Gem(said, author);
		repo.save(input);

		Gem result = repo.findById(input.getId()).get();

		assertThat(result.getSaying(), is(equalTo(said)));
		assertThat(result.getAuthor(), is(equalTo(author)));
	}

	@Ignore
	@Test
	public void shouldSaveAllFields2() {
		String said = "the said";
		String author = "TODO my by";
		Gem input = new Gem(said, author);
		repo.saveTheNewOnly(input);

		Gem result = repo.consultTheOracle("the said").get();

		assertThat(result.getSaying(), is(equalTo(said)));
		assertThat(result.getAuthor(), is(equalTo(author)));
	}

	@Ignore
	@Test
	public void shouldSaveAUnique() {
		assertThat(repo.count(), is(equalTo(0L)));

		repo.saveTheNewOnly(new Gem("a"));
		assertThat(repo.count(), is(equalTo(1L)));
	}

	@Ignore
	@Test
	public void shouldNotSaveAnExisting() {
		repo.saveTheNewOnly(new Gem("a"));
		repo.saveTheNewOnly(new Gem("a"));
		assertThat(repo.count(), is(equalTo(1L)));
	}

	@Ignore
	@Test
	public void shouldSaveTheNewOnly() {
		repo.saveTheNewOnly(new Gem("a"));
		repo.saveTheNewOnly(new Gem("a"), new Gem("b"));
		assertThat(repo.count(), is(equalTo(2L)));
	}

	@Ignore
	@Test
	public void shouldFindSimilarGem() {
		Gem gem = new Gem("how are you I am fine");
		repo.save(gem);
		
		Optional<Gem> result = repo.consultTheOracle("hello how are you");
		assertThat(gem, is(equalTo(result.get())));
	}

	@Ignore
	@Test
	public void shouldFindSimilarGemMultipleTimes() {
		Gem gem1 = new Gem("how are you I am fine");
		Gem gem2 = new Gem("how are you I am not fine");
		repo.saveAll(asList(gem1, gem2));
		
		Set<Gem> results = new HashSet<>();
		for (int i=0; i<10; i++) 
			results.add(repo.consultTheOracle("hello how are you").get());

		assertThat(results.size(), greaterThan(1));
	}

	@Ignore
	@Test
	public void shouldNotFindDifferentGem() {
		Gem gem = new Gem("how are you I am fine");
		repo.save(gem);
		
		Optional<Gem> result = repo.consultTheOracle("something completely different");
		assertThat(true, is(not(result.isPresent())));
	}
	
	@Ignore
	@Test
	public void shouldFilterByAuthor() {
		Gem gem = new Gem("hello", "Harry");
		repo.save(gem);
		
		Optional<Gem> result = repo.consultTheOracle("hello", "Harry");
		assertThat(gem, is(equalTo(result.get())));
	}

	@Ignore
	@Test
	public void shouldFilterOutByAuthor() {
		Gem gem = new Gem("hello", "Harry");
		repo.save(gem);
		
		Optional<Gem> result = repo.consultTheOracle("hello", "Harr");
		assertFalse(result.isPresent());
	}
	
	@Ignore
	@Test
	public void shouldRandomize() {
		repo.save(new Gem("one", "A"));
		repo.save(new Gem("two", "B"));
		repo.save(new Gem("three", "C"));
		repo.save(new Gem("four", "D"));
		repo.save(new Gem("five", "E"));

		Set<Gem> results = new HashSet<>();
		for (int i = 0; i<5; i++)
			results.add(repo.findRandom().get());
		assertThat(results.size(), is(not(equalTo(5))));
	}
}
