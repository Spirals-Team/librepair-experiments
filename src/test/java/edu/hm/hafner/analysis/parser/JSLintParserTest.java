package edu.hm.hafner.analysis.parser;

import org.junit.jupiter.api.Test;

import static edu.hm.hafner.analysis.assertj.Assertions.*;
import static edu.hm.hafner.analysis.assertj.SoftAssertions.*;

import edu.hm.hafner.analysis.AbstractIssueParserTest;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Priority;
import edu.hm.hafner.analysis.assertj.SoftAssertions;

/**
 * Tests the class {@link LintParser}.
 *
 * @author Gavin Mogan
 */
class JSLintParserTest extends AbstractIssueParserTest {
    private static final String EXPECTED_FILE_NAME = "duckworth/hudson-jslint-freestyle/src/prototype.js";

    JSLintParserTest() {
        super("jslint/multi.xml");
    }

    /**
     * Parses a file with one warning that are started by ant.
     *
     * @see <a href="http://issues.jenkins-ci.org/browse/JENKINS-19127">Issue 19127</a>
     */
    @Test
    void issue19127() {
        Report warnings = parse("jslint/jslint.xml");

        assertThat(warnings).hasSize(197);

        assertSoftly(softly -> {

            softly.assertThat(warnings.get(0))
                    .hasPriority(Priority.HIGH)
                    .hasCategory(JSLintXmlSaxParser.CATEGORY_UNDEFINED_VARIABLE)
                    .hasLineStart(3)
                    .hasLineEnd(3)
                    .hasMessage("'window' is not defined.")
                    .hasFileName("C:/DVR/lint_Mobile-Localization_ws/evWebService/WebClientApi/api-v1.js")
                    .hasColumnStart(5);

        });

    }

    /**
     * Tests the JS-Lint parsing for warnings in a single file.
     */
    @Test
    void testParseWithSingleFile() {
        Report results = parse("jslint/single.xml");

        assertThat(results).hasSize(51);
    }

    /**
     * Tests parsing of CSS-Lint files.
     */
    @Test
    void testCssLint() {
        Report results = parse("jslint/csslint.xml");

        assertThat(results).hasSize(51);
    }

    @Override
    protected void assertThatIssuesArePresent(final Report report, final SoftAssertions softly) {
        assertThat(report).hasSize(102);

        assertThat(report.getFiles()).hasSize(2);
        assertThat(report.getFiles()).containsExactlyInAnyOrder(EXPECTED_FILE_NAME,
                "duckworth/hudson-jslint-freestyle/src/scriptaculous.js");

        softly.assertThat(report.get(0))
                .hasPriority(Priority.HIGH)
                .hasCategory(JSLintXmlSaxParser.CATEGORY_PARSING)
                .hasLineStart(10)
                .hasLineEnd(10)
                .hasMessage("Expected 'Version' to have an indentation at 5 instead at 3.")
                .hasFileName(EXPECTED_FILE_NAME)
                .hasColumnStart(3);

    }

    @Override
    protected LintParser createParser() {
        return new LintParser();
    }
}
