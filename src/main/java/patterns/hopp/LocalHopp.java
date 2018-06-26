
package patterns.hopp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Half Object Part Protocol class.
 */
class LocalHopp implements HoppInterface {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(LocalHopp.class);

    /** The remote object proxy. */
    public HoppInterface remoteObjectProxy = new RemoteObjectProxy();

    /*
     * (non-Javadoc)
     *
     * @see patterns.hopp.HoppInterface#localMethod()
     */
    @Override
    public void localMethod() {
        LOG.info("%s.localMethod()", this.getClass().getSimpleName());
        this.remoteObjectProxy.localMethod();
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.hopp.HoppInterface#remoteMethod()
     */
    @Override
    public void remoteMethod() {
        LOG.info("%s.remoteMethod()", this.getClass().getSimpleName());
        this.remoteObjectProxy.remoteMethod();
    }
}
