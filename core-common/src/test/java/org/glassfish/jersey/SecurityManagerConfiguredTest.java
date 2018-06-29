/*
 * Copyright (c) 2014, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.jersey;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

/**
 * Test that verifies that security manager is setup to run the Jersey core server unit tests.
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
public class SecurityManagerConfiguredTest {
    /**
     * Check that system security manager has been configured.
     */
    @Test
    public void testSecurityManagerIsConfigured() {
        assertNotNull("Jersey core server unit tests should run with active security manager",
                System.getSecurityManager());
    }
}
