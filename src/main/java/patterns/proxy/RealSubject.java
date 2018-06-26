
package patterns.proxy;

/**
 * RealSubject Class.
 */
public final class RealSubject extends AbstractSubject {

    /*
     * (non-Javadoc)
     *
     * @see patterns.gof.structural.proxy.Subject#request()
     */
    @Override
    public void request() {
        this.log.info("request");
    }

}
