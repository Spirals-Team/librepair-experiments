
package patterns.singleton;

/**
 * StaticSingleton Class.
 */
public final class StaticSingleton {

    /** The instance. */
    private static StaticSingleton instance = new StaticSingleton();

    /**
     * Instantiates a new static singleton.
     */
    private StaticSingleton() {
        super();
    }

    /**
     * single instance of StaticSingleton.
     *
     * @return single instance of StaticSingleton
     */
    public static synchronized StaticSingleton getInstance() {
        return instance;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s", this.getClass().getSimpleName());
    }

}
