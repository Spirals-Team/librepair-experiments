/* (c) 2017 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.community.css.web;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.StyleInfo;
import org.geoserver.catalog.Styles;
import org.geoserver.data.test.SystemTestData;
import org.geoserver.web.GeoServerWicketTestSupport;
import org.geoserver.wms.web.data.StyleEditPage;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for recovering CSS styles generated by the old (pre-pluggable styles) CSS extension. That extension extension output styles based on
 * generated SLD files, without saving a reference to the user's CSS input, and as a result the new style editor treats them as SLD.
 *
 */
public class StyleEditCssRecoveryTest extends GeoServerWicketTestSupport {

    String oldCssStyle = "OldCssStyle";

    String oldCssStyleWithFormatSLD = "OldCssStyle_Format_Set_To_SLD";

    String oldCssStyleWithSLDManuallyEdited = "OldCssStyle_SLD_Manually_Edited";

    Catalog catalog;

    protected void setUpTestData(SystemTestData testData) throws Exception {
        super.setUpTestData(testData);
        System.out.println("setUpTestData()");

        Date t0 = new Date(1483228800000L); // Midnight Jan 1, 2017 UTC
        List<String> testStyleNames = Arrays.asList(oldCssStyle, oldCssStyleWithFormatSLD,
                oldCssStyleWithSLDManuallyEdited);

        for (String styleName : testStyleNames) {
            for (String ext : Arrays.asList(".css", ".sld", ".xml")) {
                testData.copyTo(this.getClass().getResourceAsStream(styleName + ext),
                        "styles/" + styleName + ext);
                File f = Paths.get(testData.getDataDirectoryRoot().getAbsolutePath(),
                        "styles/" + styleName + ext).toFile();
                f.setLastModified(t0.getTime());
            }
        }

        // Make this SLD file appear as if it was edited after being generated from CSS.
        File manuallyEditedSld = Paths.get(testData.getDataDirectoryRoot().getAbsolutePath(),
                "styles/" + oldCssStyleWithSLDManuallyEdited + ".sld").toFile();
        manuallyEditedSld.setLastModified(t0.getTime() + 1000000L);
    }

    @Override
    protected void onSetUp(SystemTestData testData) throws Exception {
        super.onSetUp(testData);
    }

    @Before
    public void setUp() throws Exception {
        login();
        catalog = getCatalog();
    }

    /**
     * Test recovery of a CSS Style generated by the old CSS Extension, when the StyleInfo has no declared <format> and its filename points to a
     * derived SLD.
     */
    @Test
    public void testRecoverLostCssStyle() throws Exception {
        StyleInfo styleInfo = catalog.getStyleByName(oldCssStyle);
        StyleEditPage edit = new StyleEditPage(styleInfo);

        tester.startPage(edit);
        tester.assertRenderedPage(StyleEditPage.class);
        tester.assertNoErrorMessage();

        // Assert that the page displays the format as css
        tester.assertModelValue("styleForm:context:panel:format", "css");

        // Assert that the editor text area contains css
        String editorContents = (String) tester
                .getComponentFromLastRenderedPage(
                        "styleForm:styleEditor:editorContainer:editorParent:editor")
                .getDefaultModelObject();
        Styles.handler("css").parse(editorContents, null, null, null);

        // Assert that the catalog's StyleInfo is now a css style
        StyleInfo si = catalog.getStyleByName(oldCssStyle);
        assertEquals("css", si.getFormat());
        assertEquals(oldCssStyle + ".css", si.getFilename());

    }

    /**
     * Test recovery of a CSS Style generated by the old CSS Extension, when the StyleInfo declares <format>sld</format> and its filename points to a
     * derived SLD.
     */
    @Test
    public void testRecoverLostCssStyleWithFormatSetToSLD() throws Exception {
        StyleInfo styleInfo = catalog.getStyleByName(oldCssStyleWithFormatSLD);
        StyleEditPage edit = new StyleEditPage(styleInfo);

        tester.startPage(edit);
        tester.assertRenderedPage(StyleEditPage.class);
        tester.assertNoErrorMessage();

        // Assert that the page displays the format as css
        tester.assertModelValue("styleForm:context:panel:format", "css");

        // Assert that the editor text area contains css
        String editorContents = (String) tester
                .getComponentFromLastRenderedPage(
                        "styleForm:styleEditor:editorContainer:editorParent:editor")
                .getDefaultModelObject();
        Styles.handler("css").parse(editorContents, null, null, null);

        // Assert that the catalog's StyleInfo is now a css style
        StyleInfo si = catalog.getStyleByName(oldCssStyleWithFormatSLD);
        assertEquals("css", si.getFormat());
        assertEquals(oldCssStyleWithFormatSLD + ".css", si.getFilename());
    }

    /**
     * Test that the recovery code does not overwrite generated SLD styles if they were subsequently edited.
     */
    @Test
    public void testIgnoreCssStyleIfSLDWasEdited() throws Exception {
        StyleInfo styleInfo = catalog.getStyleByName(oldCssStyleWithSLDManuallyEdited);
        StyleEditPage edit = new StyleEditPage(styleInfo);

        tester.startPage(edit);
        tester.assertRenderedPage(StyleEditPage.class);
        tester.assertNoErrorMessage();

        // Assert that the page displays the format as SLD
        tester.assertModelValue("styleForm:context:panel:format", "sld");

        // Assert that the editor text area contains SLD
        String editorContents = (String) tester
                .getComponentFromLastRenderedPage(
                        "styleForm:styleEditor:editorContainer:editorParent:editor")
                .getDefaultModelObject();
        Styles.handler("sld").parse(editorContents, null, null, null);

        // Assert that the catalog's StyleInfo is still a SLD style
        StyleInfo si = catalog.getStyleByName(oldCssStyleWithSLDManuallyEdited);
        assertEquals("sld", si.getFormat());
        assertEquals(oldCssStyleWithSLDManuallyEdited + ".sld", si.getFilename());
    }

}
