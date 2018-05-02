/*
 * Copyright Debezium Authors.
 * 
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.checkstyle;

import java.io.File;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;

/**
 * Allows disabling some rules for the test suite source. Any violation will be suppressed if it's generated by a source file
 * having {@code /src/test/java} included in its path and if the violation message contains the keyword
 * "[not required for tests]".
 * <p>
 * A SuppressionFilter is too generic, and requires per-module configuration.
 * 
 * @author Sanne Grinovero <sanne@hibernate.org>
 */
public class ExcludeTestPackages implements Filter {

    private static final String SUB_PATH = File.separator + "src" + File.separator + "test" + File.separator + "java";
    private static final String MESSAGE_DISABLE_KEYWORD = "[not required for tests]";

    @Override
    public boolean accept( AuditEvent aEvent ) {
        final String fileName = aEvent.getFileName();
        if (fileName != null && fileName.contains(SUB_PATH)) {
            return acceptTestfileEvent(aEvent);
        }
        return true;
    }

    private boolean acceptTestfileEvent( AuditEvent aEvent ) {
        final String message = aEvent.getMessage();
        if (message != null && message.contains(MESSAGE_DISABLE_KEYWORD)) {
            return false;
        }
        return true;
    }
}