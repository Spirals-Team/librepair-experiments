/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.jersey.server.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate JAX-RS resources and resource methods to provide reference to an error template if an exception has been
 * raised during processing a request (resource method invocation).
 * <p/>
 * The processing of this annotation is similar to the processing of {@link Template} annotation with the difference that the
 * thrown exception (or an object derived from the exception) is used as a model for the defined template.
 * <p/>
 * By default every {@link Exception exception} is mapped to a viewable and passed to the MVC runtime for further processing.
 * <p/>
 * Note: The {@link ErrorTemplate} annotation can be used even in case when neither {@link Viewable viewable} is used as return
 * value of a resource method nor {@link Template} annotation is used to annotate the resource method or resource class.
 *
 * @author Michal Gajdos
 * @see Template
 * @see org.glassfish.jersey.server.mvc.spi.AbstractErrorTemplateMapper
 * @since 2.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ErrorTemplate {

    /**
     * The template name that should be used to display an error raised during processing a request. The template name may be
     * declared as absolute template name if the name begins with a '/', otherwise the template name is recognized to be relative.
     */
    String name() default "";
}
