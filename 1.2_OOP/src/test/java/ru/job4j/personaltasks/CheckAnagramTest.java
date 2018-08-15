package ru.job4j.personaltasks;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Test CheckAnagram.
 * @author Igor Fedorenko (mailto:if.zommy@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class CheckAnagramTest {

    /**
     * return true.
     */
    @Test
    public void whenOriginMeatAndAnagramTeamThenReturnTrue() {
        CheckAnagram ca = new CheckAnagram();
        String orig = "meat";
        String anagram = "team";
        boolean result = ca.check(orig, anagram);
        boolean expect = true;
        assertThat(result, is(expect));
    }

    /**
     * return false.
     */
    @Test
    public void whenOriginMeatAndAnagramTimeThenReturnFalse() {
        CheckAnagram ca = new CheckAnagram();
        String orig = "meat";
        String anagram = "time";
        boolean result = ca.check(orig, anagram);
        boolean expect = false;
        assertThat(result, is(expect));
    }

}
