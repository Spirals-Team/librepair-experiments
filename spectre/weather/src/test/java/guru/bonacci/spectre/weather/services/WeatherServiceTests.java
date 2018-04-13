package guru.bonacci.spectre.weather.services;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.spectre.spectreshared.persistence.Spec;
import guru.bonacci.spectre.spectreshared.persistence.SpecRepository;
import guru.bonacci.spectre.weather.WeatherTestApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=WeatherTestApp.class, webEnvironment = NONE, properties = {
	"openweathermap.apikey=1234567890"		
})
@Ignore
public class WeatherServiceTests {

	@Autowired
	WeatherService service;
	
	@MockBean
	SpecRepository repo;

	@MockBean
	RestTemplate rest;

	@Test
	public void shouldAddData() throws Exception {
		Spec spec = new Spec();
		spec.id = "ID";
		spec.geoip.latitude = 1.1;
		spec.geoip.longitude = 2.2;
		when(repo.findById(spec.id)).thenReturn(Optional.of(spec));

		Map<String,Object> enrichmentData = new HashMap<>();
		enrichmentData.put("a", "is not b");
		doReturn(enrichmentData).when(rest).getForObject("http://api.openweathermap.org/data/2.5/weather?lat=1.1&lon=2.2&appid=1234567890", Map.class);

		service.enrich(spec.id);

		ArgumentCaptor<String> arg1 = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object> arg2 = ArgumentCaptor.forClass(Object.class);
		ArgumentCaptor<Spec> arg3 = ArgumentCaptor.forClass(Spec.class);
		verify(repo).addData(arg1.capture(), arg2.capture(), arg3.capture());

		assertThat(arg1.getValue(), is(equalTo("weather")));
		assertThat(arg2.getValue(), is(equalTo(enrichmentData)));
		assertThat(arg3.getValue(), is(equalTo(spec)));
	}
}