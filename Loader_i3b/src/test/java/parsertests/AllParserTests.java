package parsertests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ExcelParseTest.class, PasswordTest.class,
		SingletonParserTest.class})
public class AllParserTests {

}
