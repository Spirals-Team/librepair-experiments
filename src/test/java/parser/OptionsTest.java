package parser;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OptionsTest {

    private final InputKeys inputKey = InputKeys.chr;
    private final String inputArgument = "test";
    private Options options;

    @Before
    public void initialize() {
        options = new Options();
        options.setInputKey(inputKey);
        options.setInputArgument(inputArgument);
    }



    @Test
    public void get_setInputKey() {
        assertThat(inputKey).isEqualTo(options.getInputKey());
    }

    @Test
    public void get_setInputArgument() {
        assertThat(inputArgument).isEqualTo(options.getInputArgument());
    }
}