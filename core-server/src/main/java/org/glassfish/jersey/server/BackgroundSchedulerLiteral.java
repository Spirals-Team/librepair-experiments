/*
 * Copyright (c) 2015, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.jersey.server;

import org.glassfish.jersey.internal.inject.AnnotationLiteral;

/**
 * {@link BackgroundScheduler} annotation literal.
 * <p>
 * This class provides a {@link #INSTANCE constant instance} of the {@code @BackgroundScheduler} annotation to be used
 * in method calls that require use of annotation instances.
 * </p>
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 * @since 2.18
 */
@SuppressWarnings("ClassExplicitlyAnnotation")
public final class BackgroundSchedulerLiteral extends AnnotationLiteral<BackgroundScheduler> implements BackgroundScheduler {

    /**
     * {@code BackgroundScheduler} annotation instance.
     */
    public static final BackgroundScheduler INSTANCE = new BackgroundSchedulerLiteral();

    private BackgroundSchedulerLiteral() {
    }
}
