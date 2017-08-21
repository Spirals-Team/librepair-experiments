/*
 * Copyright (c) 2016-2017 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package io.moquette.test.osgi;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;

/**
 * Run a set of unit tests inside of an OSGi container to make sure
 * Moquette works in the OSGi container,
 */
@RunWith(PaxExam.class)
public class MoquetteOsgiTest {

    private static final Logger LOG = LoggerFactory.getLogger(MoquetteOsgiTest.class);

    @Inject
    private BundleContext bundleContext;

    @Configuration
    public Option[] config() {

        return options(
            junitBundles(),
            mavenBundle("io.moquette", "moquette-broker"),
            mavenBundle("io.moquette", "moquette-h2-storage"),
            mavenBundle("io.moquette", "moquette-mapdb-storage")
        );
    }

    /**
     * Look for a bundle with moquette in the name.
     *
     * <p>
     * If the OSGi headers are not in the moquette jar, this will fail.
     */
    @Test
    public void testCanFindOsgiBundle() throws Exception {
        List<String> list = new LinkedList<>(Arrays.asList("io.moquette.broker", "io.moquette.h2-storage",
                "io.moquette.mapdb-storage"));

        List<String> inactive = new LinkedList<>();

        for (Bundle b : bundleContext.getBundles()) {
            String symbolicName = b.getSymbolicName();
            LOG.info("{} {}", symbolicName, b.getState());
            System.out.println("" + symbolicName);
            if (symbolicName != null) {
                list.remove(symbolicName);
                if (b.getState() != Bundle.ACTIVE)
                    inactive.add(symbolicName);
            }
        }

        if (!inactive.isEmpty() || !list.isEmpty())
            fail("This osgi bundles are missing: " + list + " this osgi packages are NOT active" + inactive);
    }
}
