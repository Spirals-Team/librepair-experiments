package hanfak.shopofhan.infrastructure.settings;

import hanfak.shopofhan.infrastructure.properties.PropertiesReader;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class PropertiesReaderTest implements WithAssertions {

    @Test
    public void readProperty() throws Exception {
        PropertiesReader propertiesReader = new PropertiesReader("localhost");

        assertThat(propertiesReader.readProperty("server.port")).isEqualTo("8081");
    }

    @Test
    public void blowsUpIfReaderCantReadFile() throws Exception {
        try {
            new PropertiesReader("badEnvironmentName");
            fail("Should have thrown exception, failing to read bad properties file.");
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Unable to read file: badEnvironmentName.properties");
        }
    }

}