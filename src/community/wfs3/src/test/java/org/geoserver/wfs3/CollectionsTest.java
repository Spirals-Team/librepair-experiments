/* (c) 2018 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.wfs3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.jayway.jsonpath.DocumentContext;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.geoserver.catalog.FeatureTypeInfo;
import org.geoserver.data.test.MockData;
import org.geoserver.data.test.SystemTestData;
import org.geoserver.wfs.request.FeatureCollectionResponse;
import org.junit.Test;
import org.w3c.dom.Document;

public class CollectionsTest extends WFS3TestSupport {

    public static final String BASIC_POLYGONS_TITLE = "Basic polygons";
    public static final String BASIC_POLYGONS_DESCRIPTION = "I love basic polygons!";

    @Override
    protected void onSetUp(SystemTestData testData) {
        super.onSetUp(testData);

        FeatureTypeInfo basicPolygons =
                getCatalog().getFeatureTypeByName(getLayerId(MockData.BASIC_POLYGONS));
        basicPolygons.setTitle(BASIC_POLYGONS_TITLE);
        basicPolygons.setAbstract(BASIC_POLYGONS_DESCRIPTION);
        getCatalog().save(basicPolygons);
    }

    @Test
    public void testCollectionsJson() throws Exception {
        DocumentContext json = getAsJSONPath("wfs3/collections", 200);
        int expected = getCatalog().getFeatureTypes().size();
        assertEquals(expected, (int) json.read("collections.length()", Integer.class));

        // check we have the expected number of links and they all use the right "rel" relation
        List<String> formats =
                DefaultWebFeatureService30.getAvailableFormats(FeatureCollectionResponse.class);
        assertEquals(
                formats.size(), (int) json.read("collections[0].links.length()", Integer.class));
        for (String format : formats) {
            // check title and rel.
            List items = json.read("collections[0].links[?(@.type=='" + format + "')]", List.class);
            Map item = (Map) items.get(0);
            assertEquals("item", item.get("rel"));
        }
    }

    @Test
    public void testCollectionsWorkspaceSpecificJson() throws Exception {
        DocumentContext json = getAsJSONPath("cdf/wfs3/collections", 200);
        long expected =
                getCatalog()
                        .getFeatureTypes()
                        .stream()
                        .filter(ft -> "cdf".equals(ft.getStore().getWorkspace().getName()))
                        .count();
        assertEquals(expected, (int) json.read("collections.length()", Integer.class));
    }

    @Test
    public void testCollectionsXML() throws Exception {
        Document dom = getAsDOM("wfs3/collections?f=text/xml");
        print(dom);
        // TODO: add actual tests
    }

    @Test
    public void testCollectionsYaml() throws Exception {
        String yaml = getAsString("wfs3/collections/?f=application/x-yaml");
        LOGGER.log(Level.INFO, yaml);
        // TODO: add actual tests
    }

    @Test
    public void testCollectionsHTML() throws Exception {
        org.jsoup.nodes.Document document = getAsJSoup("wfs3/collections?f=html");

        // check collection links
        List<FeatureTypeInfo> featureTypes = getCatalog().getFeatureTypes();
        for (FeatureTypeInfo featureType : featureTypes) {
            String encodedName = NCNameResourceCodec.encode(featureType);
            assertNotNull(document.select("#html_" + encodedName + "_link"));
            assertEquals(
                    "http://localhost:8080/geoserver/wfs3/collections/"
                            + encodedName
                            + "/items?f=text%2Fhtml&limit=50",
                    document.select("#html_" + encodedName + "_link").attr("href"));
        }

        // go and check a specific collection title and description
        FeatureTypeInfo basicPolygons =
                getCatalog().getFeatureTypeByName(getLayerId(MockData.BASIC_POLYGONS));
        String basicPolygonsName = NCNameResourceCodec.encode(basicPolygons);
        assertEquals(
                BASIC_POLYGONS_TITLE, document.select("#" + basicPolygonsName + "_title").text());
        assertEquals(
                BASIC_POLYGONS_DESCRIPTION,
                document.select("#" + basicPolygonsName + "_description").text());
    }
}
