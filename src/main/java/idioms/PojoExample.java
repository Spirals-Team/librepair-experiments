package idioms;

/**
 * PojoExample Class.
 */
public final class PojoExample implements Cloneable {

	private String stringValue;
	private long longValue;

	/**
	 * Instantiates a new pojo example.
	 */
	public PojoExample() {
		super();
	}

	/**
	 * Instantiates a new pojo example.
	 *
	 * string value
	 * long value
	 *
	 * @param stringValue the string value
	 * @param longValue the long value
	 */
	public PojoExample(final String stringValue, final long longValue) {
		super();
		setStringValue(stringValue);
		setLongValue(longValue);
	}

	/**
	 * @param stringValue the string value
	 * @return this instance for a fluent interface.
	 */
	public PojoExample setStringValue(final String stringValue) {
		this.stringValue = stringValue;
		return this;
	}

	/**
	 * @param longValue the long value
	 * @return this instance for a fluent interface.
	 */
	public PojoExample setLongValue(final long longValue) {
		this.longValue = longValue;
		return this;
	}

	/**
	 * @return the string value
	 */
	public String getStringValue() {
		return this.stringValue;
	}

	/**
	 * @return the long value
	 */
	public long getLongValue() {
		return this.longValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.longValue ^ this.longValue >>> 32);
		result = prime * result + (this.stringValue == null ? 0 : this.stringValue.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		final PojoExample pojo = (PojoExample) obj;
		if (pojo.isLongValueEqualTo(this.longValue)) {
			if (this.stringValue == null) {
				if (pojo.isStringValueEqualTo(this.stringValue)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if is string value equal to.
	 *
	 * string value
	 *
	 * @param stringValue the string value
	 * @return true, if is
	 * 			string value equal to
	 */
	private boolean isStringValueEqualTo(final String stringValue) {
		return this.stringValue.equals(stringValue);
	}

	/**
	 * Checks if is long value equal to.
	 *
	 * long value
	 *
	 * @param longValue the long value
	 * @return true, if is
	 * 			long value equal to
	 */
	private boolean isLongValueEqualTo(final long longValue) {
		return this.longValue == longValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PojoExample clone() {
		return new PojoExample(getStringValue(), getLongValue());
	}

}
