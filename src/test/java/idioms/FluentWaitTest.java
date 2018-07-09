
package idioms;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * FluentWaitTest Class.
 */
public class FluentWaitTest {

	/**
	 * Unit Test to fluent wait.
	 */
	@Test
	public void testFluentWait() {
		final FluentWait wait = new FluentWait();
		wait.timeOut(30, TimeUnit.SECONDS);
		wait.interval(5, TimeUnit.SECONDS);
	}

	/**
	 * Unit Test to fluent wait until.
	 */
	@Test
	public void testFluentWaitUntil() {
		final FluentWait wait = new FluentWait();
		wait.until(new Condition().isTrue());
	}

	/**
	 * Unit Test to wait until condition true.
	 */
	@Test
	public void testWaitUntilConditionTrue() {
		final FluentWait wait = new FluentWait();
		wait.until(new Condition(false).isTrue());
	}

	/**
	 * Unit Test to wait until condition false.
	 */
	@Test
	public void testWaitUntilConditionFalse() {
		final FluentWait wait = new FluentWait();
		wait.until(new Condition(false).isFalse());
	}

}
