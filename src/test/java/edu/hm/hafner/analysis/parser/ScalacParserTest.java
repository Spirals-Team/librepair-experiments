package edu.hm.hafner.analysis.parser;

import edu.hm.hafner.analysis.AbstractIssueParserTest;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Priority;
import edu.hm.hafner.analysis.assertj.SoftAssertions;

/**
 * Tests the class {@link ScalacParser}.
 *
 * @author Alexey Kislin
 */
class ScalacParserTest extends AbstractIssueParserTest {
    ScalacParserTest() {
        super("scalac.txt");
    }

    private static final String SCALAC_CATEGORY_WARNING = "warning";

    @Override
    protected void assertThatIssuesArePresent(final Report report, final SoftAssertions softly) {
        softly.assertThat(report).hasSize(3);

        softly.assertThat(report)
                .hasSize(3).hasPriorities(1, 2, 0);
        softly.assertThat(report.get(0))
                .hasPriority(Priority.NORMAL)
                .hasCategory(SCALAC_CATEGORY_WARNING)
                .hasLineStart(29)
                .hasLineEnd(29)
                .hasMessage("implicit conversion method toLab2OI should be enabled")
                .hasFileName("/home/user/.jenkins/jobs/job/workspace/some/path/SomeFile.scala");
        softly.assertThat(report.get(1))
                .hasPriority(Priority.NORMAL)
                .hasCategory(SCALAC_CATEGORY_WARNING)
                .hasLineStart(408)
                .hasLineEnd(408)
                .hasMessage("method asJavaMap in object JavaConversions is deprecated: use mapAsJavaMap instead")
                .hasFileName("/home/user/.jenkins/jobs/job/workspace/another/path/SomeFile.scala");
        softly.assertThat(report.get(2))
                .hasPriority(Priority.HIGH)
                .hasCategory(SCALAC_CATEGORY_WARNING)
                .hasLineStart(59)
                .hasLineEnd(59)
                .hasMessage("method error in object Predef is deprecated: Use `sys.error(message)` instead")
                .hasFileName("/home/user/.jenkins/jobs/job/workspace/yet/another/path/SomeFile.scala");
    }

    @Override
    protected ScalacParser createParser() {
        return new ScalacParser();
    }
}
