/* (c) 2018 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.wfs3;

import static org.junit.Assert.assertEquals;

import com.jayway.jsonpath.DocumentContext;
import java.util.List;
import java.util.Map;
import org.custommonkey.xmlunit.XMLAssert;
import org.geoserver.data.test.MockData;
import org.geoserver.wfs.request.FeatureCollectionResponse;
import org.junit.Test;
import org.w3c.dom.Document;

public class CollectionTest extends WFS3TestSupport {

    @Test
    public void testCollectionJson() throws Exception {
        DocumentContext json =
                getAsJSONPath("wfs3/collections/" + getEncodedName(MockData.ROAD_SEGMENTS), 200);

        assertEquals("cite__RoadSegments", json.read("$.name", String.class));
        assertEquals("RoadSegments", json.read("$.title", String.class));
        assertEquals(-180, json.read("$.extent.spatial[0]", Double.class), 0d);
        assertEquals(-90, json.read("$.extent.spatial[1]", Double.class), 0d);
        assertEquals(180, json.read("$.extent.spatial[2]", Double.class), 0d);
        assertEquals(90, json.read("$.extent.spatial[3]", Double.class), 0d);

        // check we have the expected number of links and they all use the right "rel" relation
        List<String> formats =
                DefaultWebFeatureService30.getAvailableFormats(FeatureCollectionResponse.class);
        assertEquals(formats.size(), (int) json.read("$.links.length()", Integer.class));
        for (String format : formats) {
            // check title and rel.
            List items = json.read("$.links[?(@.type=='" + format + "')]", List.class);
            Map item = (Map) items.get(0);
            assertEquals("cite__RoadSegments items as " + format, item.get("title"));
            assertEquals("item", item.get("rel"));
        }
    }

    @Test
    public void testCollectionsXML() throws Exception {
        Document dom =
                getAsDOM(
                        "wfs3/collections/"
                                + getEncodedName(MockData.ROAD_SEGMENTS)
                                + "?f=text/xml");
        print(dom);
        String expected =
                "http://localhost:8080/geoserver/wfs3/collections/cite__RoadSegments/items?f=application%2Fjson";
        XMLAssert.assertXpathEvaluatesTo(
                expected,
                "//wfs:Collection[wfs:Name='cite__RoadSegments']/atom:link[@atom:type='application/json']/@atom:href",
                dom);
    }

    @Test
    public void testCollectionYaml() throws Exception {
        String yaml =
                getAsString(
                        "wfs3/collections/"
                                + getEncodedName(MockData.ROAD_SEGMENTS)
                                + "?f=application/x-yaml");
        System.out.println(yaml);
    }
}
