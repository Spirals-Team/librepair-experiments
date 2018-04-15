package src;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestsDataframe.class, TestsColumnsSelection.class, TestsStats.class, TestDataframe.class, TestsLinesSelection.class})
public class DataframeTestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
