package org.hibernate.search.util.impl.common.logging;

import java.io.Serializable;
import javax.annotation.Generated;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.BasicLogger;
import java.lang.Runnable;
import java.lang.String;
import org.jboss.logging.Logger;
import java.util.Arrays;
import java.lang.IllegalArgumentException;

/**
 * Warning this class consists of generated code.
 */
@Generated(value = "org.jboss.logging.processor.generator.model.MessageLoggerImplementor", date = "2018-06-01T10:38:51+0200")
public class Log_$logger extends DelegatingBasicLogger implements Log,BasicLogger,Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FQCN = Log_$logger.class.getName();
    public Log_$logger(final Logger log) {
        super(log);
    }
    @Override
    public final void interruptedWorkError(final Runnable r) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.ERROR, null, interruptedWorkError$str(), r);
    }
    private static final String interruptedWorkError = "HSEARCH-UTIL000017: Work discarded, thread was interrupted while waiting for space to schedule: %1$s";
    protected String interruptedWorkError$str() {
        return interruptedWorkError;
    }
    private static final String mustNotBeNull = "HSEARCH-UTIL000018: '%1$s' must not be null.";
    protected String mustNotBeNull$str() {
        return mustNotBeNull;
    }
    @Override
    public final IllegalArgumentException mustNotBeNull(final String objectDescription) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(mustNotBeNull$str(), objectDescription));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
}
