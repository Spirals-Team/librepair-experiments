/*
 * OrbisWPS contains a set of libraries to build a Web Processing Service (WPS)
 * compliant with the 2.0 specification.
 *
 * OrbisWPS is part of the OrbisGIS platform
 *
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 *
 * OrbisWPS is distributed under GPL 3 license.
 *
 * Copyright (C) 2015-2017 CNRS (Lab-STICC UMR CNRS 6285)
 *
 *
 * OrbisWPS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisWPS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisWPS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.orbiswps.service.utils;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTWriter;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Sylvain PALOMINOS
 */
public class DataProcessingTest {

    /** WKT writer. */
    private WKTWriter wktWriter = new WKTWriter();

    /**
     * Test the processing of an incoming BoundingBox and if the geometry generated is correct.
     */
    @Test
    public void testBoundingBoxProcessing() {
        Geometry geometry = null;
        try {
            geometry = WpsDataUtils.parseStringToBoundingBox("EPSG:4326;0,0,1,1");
        } catch (ParseException ignored) {}
        Assert.assertNotNull("The bounding box geometry should not be null.",
                geometry);
        Assert.assertEquals("The bounding box geometry dimension should be 2.",
                2, geometry.getDimension());
        Assert.assertEquals("The bounding box geometry SRID should be 4326.",
                4326, geometry.getSRID());
        Assert.assertEquals("The bounding box geometry wasn't the one expected.",
                "POLYGON ((0 0, 1 0, 1 1, 0 1, 0 0))", wktWriter.write(geometry));

        String str = WpsDataUtils.parseBoundingBoxToString(geometry);
        Assert.assertEquals("The bounding box geometry wasn't the one expected.",
                ":4326;0,0,1,1", str);
    }

    /**
     * Test the preprocessing of a 3D bounding box (now not supported).
     */
    @Test(expected = ParseException.class)
    public void test3DBoundingBoxProcessing() throws ParseException {
        WpsDataUtils.parseStringToBoundingBox("EPSG:4326;0,0,0,1,1,1");
    }

    /**
     * Test the preprocessing of an incoming GeometryData and if the geometry generated is correct.
     * Then check its postprocessing and if its string representation is correct.
     */
    @Test
    public void testGeometryProcessing() {
        Geometry geometry = null;
        try {
            geometry = WpsDataUtils.parseStringToGeometry("POLYGON ((0 0, 1 0, 1 1, 0 1, 0 0))");
        } catch (ParseException ignored) {}
        Assert.assertNotNull("The geometry get from the GeometryProcessing should not be null", geometry);
        String string = WpsDataUtils.parseGeometryToString(geometry);
        Assert.assertEquals("The geometry wasn't the one expected.",
                "POLYGON ((0 0, 1 0, 1 1, 0 1, 0 0))", string);
    }
}
