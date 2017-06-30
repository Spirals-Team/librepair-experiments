/* (c) 2014 - 2016 Open Source Geospatial Foundation - all rights reserved
 * (c) 2001 - 2014 OpenPlans
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;

import javax.servlet.ServletContextEvent;

import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.factory.GeoTools;
import org.geotools.factory.Hints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.FeatureFactory;
import org.opengis.filter.FilterFactory;
import org.opengis.style.StyleFactory;

import org.springframework.mock.web.MockServletContext;

public class GeoServerInitStartupListenerTest {

    private static final double CUSTOM_TOLERANCE = 1E-7d;

    @Before
    public void init() {
        System.setProperty("COMPARISON_TOLERANCE", Double.toString(CUSTOM_TOLERANCE));
    }

    @After
    public void reset() {
        System.clearProperty("COMPARISON_TOLERANCE");
    }

    @Test
    public void testStartupListener() {
        GeoserverInitStartupListener listener = new GeoserverInitStartupListener();
        MockServletContext ctx = new MockServletContext();
        listener.contextInitialized(new ServletContextEvent(ctx));
        Hints hints = GeoTools.getDefaultHints();

        final Object factory = hints.get(Hints.GRID_COVERAGE_FACTORY);
        assertNotNull(factory);
        assertTrue(factory instanceof GridCoverageFactory);

        final Object datumShift = hints.get(Hints.LENIENT_DATUM_SHIFT);
        assertNotNull(datumShift);
        assertTrue((Boolean) datumShift);

        final Object tolerance = hints.get(Hints.COMPARISON_TOLERANCE);
        assertNotNull(tolerance);
        assertEquals(CUSTOM_TOLERANCE, (Double) tolerance, 1e-12d);

        final Object filterFactory = hints.get(Hints.FILTER_FACTORY);
        assertNotNull(filterFactory);
        assertTrue(filterFactory instanceof FilterFactory);

        final Object styleFactory = hints.get(Hints.STYLE_FACTORY);
        assertNotNull(styleFactory);
        assertTrue(styleFactory instanceof StyleFactory);

        final Object featureFactory = hints.get(Hints.FEATURE_FACTORY);
        assertNotNull(featureFactory);
        assertTrue(featureFactory instanceof FeatureFactory);

        final Object executorService = hints.get(Hints.EXECUTOR_SERVICE);
        assertNotNull(executorService);
        assertTrue(executorService instanceof ExecutorService);

    }
}