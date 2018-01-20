package hanfak.shopofhan.infrastructure.settings;

import hanfak.shopofhan.infrastructure.properties.PropertiesReader;
import hanfak.shopofhan.infrastructure.properties.Settings;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SettingsTest implements WithAssertions {
    @Test
    public void mySqlDbPortTest() throws Exception {
        PropertiesReader properties = mock(PropertiesReader.class);
        Settings settings = new Settings(properties);

        when(properties.readProperty("server.port")).thenReturn("8081");

        assertThat(settings.serverPort()).isEqualTo(8081);
    }

    @Test
    public void databaseUrlTest() throws Exception {
        PropertiesReader properties = mock(PropertiesReader.class);
        Settings settings = new Settings(properties);

        when(properties.readProperty("database.shopOfHanUrl")).thenReturn("jdbc:mysql://172.17.0.3:3306/");

        assertThat(settings.databaseURL()).isEqualTo("jdbc:mysql://172.17.0.3:3306/");
    }

    @Test
    public void databaseUsernameTest() throws Exception {
        PropertiesReader properties = mock(PropertiesReader.class);
        Settings settings = new Settings(properties);

        when(properties.readProperty("database.username")).thenReturn("admin");

        assertThat(settings.databaseUsername()).isEqualTo("admin");
    }

    @Test
    public void databasePasswordTest() throws Exception {
        PropertiesReader properties = mock(PropertiesReader.class);
        Settings settings = new Settings(properties);

        when(properties.readProperty("database.password")).thenReturn("password");

        assertThat(settings.databasePassword()).isEqualTo("password");
    }
}