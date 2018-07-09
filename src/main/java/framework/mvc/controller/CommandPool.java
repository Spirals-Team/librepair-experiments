/** CommandPool.java
 * Created on 17 April 2006, 21:02
 */
package framework.mvc.controller;

/**
 * CommandPool Class.
 */
public class CommandPool {

	/** The instance. */
	private static CommandPool instance;

	/**
	 * Creates the.
	 *
	 * command pool
	 */
	public static CommandPool create() {
		return new CommandPool();
	}

	/**
	 * single instance of CommandPool.
	 *
	 * @return single instance of CommandPool
	 */
	public static CommandPool getInstance() {
		if (CommandPool.instance == null) {
			CommandPool.instance = create();
		}
		return CommandPool.instance;
	}

	/**
	 * Instantiates a new command pool.
	 */
	private CommandPool() {
	}

}
