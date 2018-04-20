package org.sql2o.logging;

/**
 * Created by lars on 2/9/14.
 */
public interface Logger {

    void debug(String format, Object[] argArray);

    void debug(String format, Object arg);

    void warn(String format);

    void warn(String format, Throwable exception);
}
