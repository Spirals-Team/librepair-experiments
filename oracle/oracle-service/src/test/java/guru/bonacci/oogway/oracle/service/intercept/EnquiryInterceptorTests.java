package guru.bonacci.oogway.oracle.service.intercept;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.service.OracleTestApp;
import guru.bonacci.oogway.oracle.service.events.OracleGateway;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.shareddomain.GenericEvent;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=OracleTestApp.class, webEnvironment=NONE)
@ActiveProfiles("unit-test")
public class EnquiryInterceptorTests {

	@Autowired
	GemRepository repo;

	@MockBean
	OracleGateway gateway;

	@Captor
	ArgumentCaptor<GenericEvent> captor;
	
	@Test
	public void shouldInterceptTheConsultMethodWithoutAuthor() {
		String searchString = "something completely different";
		repo.consultTheOracle(searchString);

		verify(gateway, times(1)).send(captor.capture());
		assertThat(captor.getValue().getContent(), is(equalTo(searchString)));
	}

	@Test
	public void shouldInterceptTheConsultMethodWithAuthor() {
		String searchString = "something completely different";
		repo.consultTheOracle(searchString, "some author");

		verify(gateway, times(1)).send(captor.capture());
		assertThat(captor.getValue().getContent(), is(equalTo(searchString)));
	}
}
