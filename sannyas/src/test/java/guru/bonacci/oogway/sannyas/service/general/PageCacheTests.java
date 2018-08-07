package guru.bonacci.oogway.sannyas.service.general;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.oogway.sannyas.service.general.PageTestCaches.PageTestCache1;
import guru.bonacci.oogway.sannyas.service.general.PageTestCaches.PageTestCache2;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = NONE)
@ActiveProfiles("cache-test")
public class PageCacheTests {

	@Autowired 
	PageTestCache1 pageCache;

	@Autowired 
	PageTestCache2 pageCache2;

	@Test
	public void shouldCacheOnTwoBeansWithSameNamedCache() {
		int nr = pageCache.getNrOfPages("first call");
		assertThat(nr, is(equalTo(1)));

		nr = pageCache.getNrOfPages("first call");
		assertThat(nr, is(equalTo(1)));

		nr = pageCache.getNrOfPages("second call");
		assertThat(nr, is(equalTo(2)));

		nr = pageCache2.getNrOfPages("first call");
		assertThat(nr, is(equalTo(1))); //first call is cached by the other bean
	}
	
	@SpringBootApplication
	@EnableCaching(proxyTargetClass=true)
	@ComponentScan
	@Profile("cache-test")
	static class PageCacheTestApp {

		static void main(String[] args) {
			SpringApplication.run(PageCacheTestApp.class, args);
		}
	}
}
