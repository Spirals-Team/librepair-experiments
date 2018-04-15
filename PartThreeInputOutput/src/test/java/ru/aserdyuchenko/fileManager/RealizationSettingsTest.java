package ru.aserdyuchenko.fileManager;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.io.InputStream;

/**
 * @author Anton Serdyuchenko (anton415@gmail.com).
 * jUnit test for class RealizationSettings.
 */
public class RealizationSettingsTest {
/**
 * Test load app.properties.
 * @throws Exception - Exception.
 */
	@Test
	public void whenClassLoader() throws Exception {
		RealizationSettings settings = new RealizationSettings();
		ClassLoader loader = RealizationSettings.class.getClassLoader();
		try (InputStream io = loader.getResourceAsStream("app.properties")) {
			settings.load(io);
		}
		String value = settings.getValue("number.port");
		assertThat(value, is("5000"));
	}
}
