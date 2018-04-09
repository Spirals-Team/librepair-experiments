package app;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	EntitiesTest.class,
	LoginControllerTest.class,
	MainControllerTest.class
})
public class Tests { }
