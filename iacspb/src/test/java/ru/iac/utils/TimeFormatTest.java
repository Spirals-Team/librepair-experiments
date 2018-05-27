package ru.iac.utils;

import org.junit.Test;
import java.sql.Timestamp;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TimeFormatTest {

    @Test
    public void whenTestTimeFormat() {
        Timestamp time = new Timestamp((long) 0);
        TimeFormat.getTime(time);
        assertThat(TimeFormat.getTime(time), is("1.01.1970 03:00"));
    }
}