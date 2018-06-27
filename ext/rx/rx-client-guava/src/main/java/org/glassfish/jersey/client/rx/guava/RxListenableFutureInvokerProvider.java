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

package org.glassfish.jersey.client.rx.guava;

import java.util.concurrent.ExecutorService;

import javax.ws.rs.client.RxInvokerProvider;
import javax.ws.rs.client.SyncInvoker;

/**
 * Invoker provider for invokers based on Guava's {@code ListenableFuture}.
 *
 * @author Michal Gajdos
 * @author Pavel Bucek (pavel.bucek at oracle.com)
 * @since 2.13
 */
public final class RxListenableFutureInvokerProvider implements RxInvokerProvider<RxListenableFutureInvoker> {

    @Override
    public boolean isProviderFor(Class clazz) {
        return RxListenableFutureInvoker.class.equals(clazz);
    }

    @Override
    public JerseyRxListenableFutureInvoker getRxInvoker(SyncInvoker syncInvoker, ExecutorService executorService) {
        return new JerseyRxListenableFutureInvoker(syncInvoker, executorService);
    }
}
