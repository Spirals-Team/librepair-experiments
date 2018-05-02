/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.processors.solr.kerberos;

import org.apache.commons.lang3.Validate;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.nifi.processors.solr.kerberos.KerberosKeytabConfiguration.APP_NAME;

/**
 * Main entry point for Solr processors to authenticate and execute actions when Kerberos is enabled.
 */
public class KerberosKeytabUser {

    private final String principal;
    private final String keytabFile;
    private final AtomicBoolean loggedIn = new AtomicBoolean(false);

    private Subject subject;
    private LoginContext loginContext;

    public KerberosKeytabUser(final String principal, final String keytabFile) {
        this.principal = principal;
        this.keytabFile = keytabFile;
        Validate.notBlank(principal);
        Validate.notBlank(keytabFile);
    }

    /**
     * Performs a login using the specified principal and keytab.
     *
     * @throws LoginException if the login fails
     */
    public synchronized void login() throws LoginException {
        if (isLoggedIn()) {
            return;
        }

        try {
            // if it's the first time ever calling login then we need to initialize a new context
            if (loginContext == null) {
                this.subject = new Subject();
                this.loginContext = new LoginContext(APP_NAME, subject, null,
                        new KerberosKeytabConfiguration(principal, keytabFile));
            }

            loginContext.login();
            loggedIn.set(true);

        } catch (LoginException le) {
            throw new LoginException("Unable to login with " + principal + " and " + keytabFile + " due to: " + le.getMessage());
        }
    }

    /**
     * Performs a logout of the current user.
     *
     * @throws LoginException if the logout fails
     */
    public synchronized void logout() throws LoginException {
        if (!isLoggedIn()) {
            return;
        }

        try {
            loginContext.logout();
            loggedIn.set(false);

            subject = null;
            loginContext = null;
        } catch (LoginException e) {
            throw new LoginException("Logout failed due to: " + e.getMessage());
        }
    }

    /**
     * Executes the PrivilegedAction as this user.
     *
     * @param action the action to execute
     * @param <T> the type of result
     * @return the result of the action
     * @throws IllegalStateException if this method is called while not logged in
     */
    public <T> T doAs(final PrivilegedAction<T> action) throws IllegalStateException {
        if (!isLoggedIn()) {
            throw new IllegalStateException("Must login before executing actions");
        }

        return Subject.doAs(subject, action);
    }

    /**
     * @return true if this user is currently logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn.get();
    }

    /**
     * @return the principal for this user
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * @return the keytab file for this user
     */
    public String getKeytabFile() {
        return keytabFile;
    }

}
