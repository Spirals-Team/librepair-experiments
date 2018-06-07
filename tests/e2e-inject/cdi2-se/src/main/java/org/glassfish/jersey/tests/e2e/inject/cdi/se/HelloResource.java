/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.jersey.tests.e2e.inject.cdi.se;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.inject.Inject;

/**
 * Intercepted and decorated resource.
 *
 * @author Petr Bouda
 */
@Secured
@Path("intercepted")
public class HelloResource implements Hello {

    @Inject
    private NameService service;

    @GET
    @Override
    public String hello() {
        return "Hello " + service.getName();
    }
}
