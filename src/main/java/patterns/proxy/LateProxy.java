
package patterns.proxy;

/**
 * LateProxy Class.
 */
public final class LateProxy extends AbstractSubject {

    /** The real subject. */
    private RealSubject realSubject;

    /*
     * (non-Javadoc)
     *
     * @see patterns.gof.structural.proxy.Subject#request()
     */
    @Override
    public void request() {
        if (this.realSubject == null) {
            this.realSubject = new RealSubject();
        }
        this.realSubject.request();
    }

}
