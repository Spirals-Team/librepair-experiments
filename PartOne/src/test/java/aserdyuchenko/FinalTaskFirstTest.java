package aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест проверки, что строка sub является подстрокой origin.
 * Подробное описание алгоритма, расписано в классе FinalTaskFirst.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.08.2016
 */
public class FinalTaskFirstTest {
/**
 * whenOriginStringIsConvertedToAnOriginArray().
 */
    @Test
    public void whenOriginStringIsConvertedToAnOriginArray() {
        FinalTaskFirst finalTaskFirst = new FinalTaskFirst();
        String[] originArray = finalTaskFirst.convertOrigin("1 2 3");
        assertThat(originArray, is(new String[]{"1", "2", "3"}));
    }
/**
 * whenSubStringIsConvertedToAnSubArray().
 */
    @Test
    public void whenSubStringIsConvertedToAnSubArray() {
        FinalTaskFirst finalTaskFirst = new FinalTaskFirst();
        String[] subArray = finalTaskFirst.convertSub("1 2");
        assertThat(subArray, is(new String[]{"1", "2"}));
    }
/**
 * whenStringSubIsSubstringOfOrigin().
 */
    @Test
    public void whenStringSubIsSubstringOfOrigin() {
        FinalTaskFirst finalTaskFirst = new FinalTaskFirst();
        String[] originArray = {"1", "2", "3"};
        String[] subArray = {"1", "2"};
        boolean result = finalTaskFirst.comparison(originArray, subArray);
        assertThat(result, is(true));
    }
/**
 * whenStringSubIsNotSubstringOfOrigin().
 */
    @Test
    public void whenStringSubIsNotSubstringOfOrigin() {
        FinalTaskFirst finalTaskFirst = new FinalTaskFirst();
        String[] originArray = {"1", "2", "3"};
        String[] subArray = {"4", "5"};
        boolean result = finalTaskFirst.comparison(originArray, subArray);
        assertThat(result, is(false));
    }
}