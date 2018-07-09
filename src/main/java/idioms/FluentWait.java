
package idioms;

import java.util.concurrent.TimeUnit;

/**
 * FluentWait Class.
 */
public class FluentWait {

	private long timeOut;
	private long interval;

	/**
	 * Time out.
	 *
	 * timeout
	 * time unit
	 *
	 * @param timeout the timeout
	 * @param timeUnit the time unit
	 */
	public void timeOut(long timeout, TimeUnit timeUnit) {
		timeOut = timeout;
	}

	/**
	 * Interval.
	 *
	 * interval
	 * time unit
	 *
	 * @param interval the interval
	 * @param timeUnit the time unit
	 */
	public void interval(long interval, TimeUnit timeUnit) {
		this.interval = interval;
	}

	/**
	 * Ignore.
	 *
	 * class 1
	 *
	 * @param class1 the class 1
	 */
	public void ignore(Class<Exception> class1) {
		// TODO Auto-generated method stub
	}

	/**
	 * Until.
	 *
	 * b
	 *
	 * @param b the b
	 */
	public void until(boolean b) {
		// TODO Auto-generated method stub
	}

}
