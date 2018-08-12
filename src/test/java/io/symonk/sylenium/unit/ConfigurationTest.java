package io.symonk.sylenium.unit;

import io.symonk.sylenium.SyConfig;
import org.testng.annotations.Test;

import static io.symonk.sylenium.SyConfig.rebuildConfiguration;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * $ylenium configuration unit tests
 * @NotThreadSafe -> run these tests on a single thread to avoid failure(s)
 */
public class ConfigurationTest {

    @Test
    public void $waitOverrideIsCorrect() {
        System.setProperty("sylenium.wait", "100000");
        rebuildConfiguration();
        assertThat(SyConfig.$wait).isEqualTo(100000);
    }

    @Test
    public void $pollOverrideIsCorrect() {
        System.setProperty("sylenium.poll", "19999");
        rebuildConfiguration();
        assertThat(SyConfig.$poll).isEqualTo(19999);
    }

    @Test
    public void $waitDefaultIsCorrect() {
        assertThat(SyConfig.$wait).isEqualTo(5000);
    }

    @Test
    public void $pollDefaultIsCorrect() {
        assertThat(SyConfig.$poll).isEqualTo(100);
    }

    @Test
    public void $browserDefaultIsCorrect() {
        assertThat(SyConfig.$browser).isEqualToIgnoringCase("chrome");
    }

    @Test
    public void $browserOverrideIsCorrect() {
        System.setProperty("sylenium.browser", "firefox");
        rebuildConfiguration();
        assertThat(SyConfig.$browser).isEqualTo("firefox");
    }

    @Test
    public void $headlessBrowserDefault() {
        assertThat(SyConfig.$headless).isEqualTo(false);
    }

    @Test
    public void $headlessOverrideIsCorrect() {
        System.setProperty("sylenium.headless", "true");
        rebuildConfiguration();
        assertThat(SyConfig.$headless).isEqualTo(true);
    }

    @Test
    public void $distributedDefaultIsCorrect() {
        assertThat(SyConfig.$distributed).isEqualTo(false);
    }

    @Test
    public void $distributedOverrideIsCorrect() {
        System.setProperty("sylenium.distributed", "true");
        rebuildConfiguration();
        assertThat(SyConfig.$distributed).isEqualTo(true);
    }

    @Test
    public void $poweredDefaultIsCorrect() {
        assertThat(SyConfig.$powered).isEqualTo(true);
    }

    @Test
    public void $poweredOverrideIsCorrect() {
        System.setProperty("sylenium.powered", "false");
        rebuildConfiguration();
        assertThat(SyConfig.$powered).isEqualTo(false);
    }

    @Test
    public void $maximizeDefaultIsCorrect() {
        assertThat(SyConfig.$maximize).isEqualTo(true);
    }

    @Test
    public void $maximizeOverrideIsCorrect() {
        System.setProperty("sylenium.maximized", "false");
        rebuildConfiguration();
        assertThat(SyConfig.$maximize).isEqualTo(false);
    }


}
