/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * GNU General Public License as published by
 * License, or
 * (at your option) any later version.
 *
 * hope that it will be useful,
 * implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package coaching.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadTemplate implements Runnable {

	private static final long TIME_OUT = 1000;
	private static final long MAX_TICKS = 10;
	protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	protected ThreadConfig config;
	protected final Thread thread;
	protected boolean exit = false;
	protected long tick;
	protected long startTime;
	protected long timeOut = TIME_OUT;
	protected long maxTicks = MAX_TICKS;

	public ThreadTemplate() {
		initialise(new ThreadConfig());
		this.thread = new java.lang.Thread(this);
	}

	/**
	 * Initialise.
	 *
	 * configuration element
	 */
	public void initialise(final ThreadConfig config) {
		this.config = config;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		do {
			this.tick++;

			final String className = this.getClass().getSimpleName();
			final String threadName = this.thread.getName();
			final int priority = this.thread.getPriority();
			this.log.info("classname:{}:threadName:{}({}).{}", className, threadName, priority, this.tick);

			try {
				execute();
			} catch (final ApplicationException exception) {
				this.log.error("{}", exception);
			}

			// Yield a little.
			java.lang.Thread.yield();

			// * Thread ends.
			if (this.tick >= this.maxTicks) {
				this.exit = true;
			}

			final long currentTimeMillis = System.currentTimeMillis();
			if (currentTimeMillis - this.startTime > TIME_OUT) {
				this.exit = true;
			}
		} while (!this.exit);
	}

	protected void execute() throws ApplicationException {
		throw new ApplicationException("execute method must be overridden");
	}

	/**
	 * Start.
	 */
	public void start() {
		this.thread.start();
	}

	/**
	 * Stop.
	 */
	public void stop() {
		this.exit = true;
	}

}
