package idioms;

/**
 * Condition Class.
 */
public class Condition implements ConditionInterface<Boolean> {
	private boolean result = false;

	/**
	 * Instantiates a new condition.
	 */
	public Condition() {
	}

	public Condition(final boolean result) {
		this.result = result;
	}

	/**
	 * Checks if is true.
	 *
	 * @return true, if is
	 * 			true
	 */
	public boolean isTrue() {
		return this.result == true;
	}

	public boolean isFalse() {
		return this.result == false;
	}

}
