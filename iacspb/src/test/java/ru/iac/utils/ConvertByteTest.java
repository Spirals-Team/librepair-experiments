package ru.iac.utils;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ConvertByteTest {

    @Test
    public void testConvertByte() {
        assertThat(ConvertByte.conv(1), is("1 b"));
        assertThat(ConvertByte.conv(1023), is("1023 b"));
    }

    @Test
    public void testConvertKByte() {
        assertThat(ConvertByte.conv(1024), is("1 Kb"));
        assertThat(ConvertByte.conv(1048570), is("1023,99 Kb"));
    }

    @Test
    public void testConvertMByte() {
        assertThat(ConvertByte.conv(1048576), is("1 Mb"));
        assertThat(ConvertByte.conv(1073730000), is("1023,99 Mb"));
    }

    @Test
    public void testConvertGByte() {
        assertThat(ConvertByte.conv(1073741824), is("1 Gb"));
        assertThat(ConvertByte.conv(2073741824), is("1,93 Gb"));
    }
}