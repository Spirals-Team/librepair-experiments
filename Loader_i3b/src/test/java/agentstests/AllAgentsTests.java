package agentstests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PersonAgentTest.class, EntityAgentTest.class, GeneralAgentTest.class, 
	SensorAgentTest.class})
public class AllAgentsTests {

}
