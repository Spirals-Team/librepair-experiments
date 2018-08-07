package guru.bonacci.spectre.weather.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.spectre.spectreshared.persistence.Spec;
import guru.bonacci.spectre.spectreshared.persistence.SpecRepository;
import guru.bonacci.spectre.weather.WeatherTestApp;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=WeatherTestApp.class, webEnvironment = NONE, properties = {
	"openweathermap.apikey=1234567890"		
})
@Disabled
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

		assertEquals(arg1.getValue(), "weather");
		assertEquals(arg2.getValue(), enrichmentData);
		assertEquals(arg3.getValue(), spec);
	}
}