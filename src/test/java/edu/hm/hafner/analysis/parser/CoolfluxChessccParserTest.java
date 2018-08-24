package edu.hm.hafner.analysis.parser;

import edu.hm.hafner.analysis.AbstractIssueParserTest;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Priority;
import edu.hm.hafner.analysis.assertj.SoftAssertions;

/**
 * Tests the class {@link CoolfluxChessccParserTest}.
 */
class CoolfluxChessccParserTest extends AbstractIssueParserTest {

    /**
     * Creates a new CoolfluxChessccParserTest.
     */
    CoolfluxChessccParserTest() {
        super("coolfluxchesscc.txt");
    }

    @Override
    protected void assertThatIssuesArePresent(final Report report, final SoftAssertions softly) {
        softly.assertThat(report).hasSize(1).hasDuplicatesSize(1);

        softly.assertThat(report.get(0))
                .hasLineStart(150)
                .hasLineEnd(150)
                .hasMessage(
                        "function `unsigned configureRealCh(unsigned)' was declared static, but was not defined")
                .hasFileName("/nfs/autofs/nett/nessie6/dailies/Monday/src/n6/heidrun/dsp/Modules/LocalChAdmin.c")
                .hasCategory(DEFAULT_CATEGORY)
                .hasPriority(Priority.HIGH);
    }

    @Override
    protected CoolfluxChessccParser createParser() {
        return new CoolfluxChessccParser();
    }
}

