
package coaching.idioms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegularExpression Class.
 */
public class RegularExpression {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(RegularExpression.class);

    /** The Constant PATTERN_STRING. */
    private static final String PATTERN_STRING = "^ABC$";

    /** The pattern. */
    private Pattern pattern = Pattern.compile(PATTERN_STRING);

    /**
     * Instantiates a new regular expression.
     */
    public RegularExpression() {
        super();
    }

    /**
     * Instantiates a new regular expression.
     *
     * pattern string
     *
     * @param patternString
     *            the pattern string
     */
    public RegularExpression(final String patternString) {
        super();
        this.pattern = Pattern.compile(patternString);
    }

    /**
     * Instantiates a new regular expression.
     *
     * pattern
     *
     * @param pattern
     *            the pattern
     */
    public RegularExpression(final Pattern pattern) {
        super();
        this.pattern = pattern;
    }

    /**
     * Verify that.
     *
     * code
     *
     * @param code
     *            the code
     * @return true, if successful, otherwise false.
     */
    public boolean verify(final String code) {
        final Matcher matcher = this.pattern.matcher(code);
        return matcher.find();
    }

    /**
     * Find.
     *
     * original
     *
     * @param original
     *            the original
     */
    public void find(final String original) {
        final Matcher matcher = this.pattern.matcher(original);
        while (matcher.find()) {
            final String message = "Test %s starting at index %s and ending at index %s";
            final String string = String.format(message, matcher.group(), matcher.start(), matcher.end());
            LOG.info(string);
        }
    }

    /**
     * Replace.
     *
     * original new sub string string
     *
     * @param original
     *            the original
     * @param newSubString
     *            the new sub string
     * @return the string
     */
    public String replace(final String original, final String newSubString) {
        // final Matcher matcher = this.PATTERN.matcher(original);
        return this.pattern.matcher(original).replaceAll(newSubString);
    }

}
