package org.esa.s2tbx.dataio.gdal.writer.plugins;

import org.esa.s2tbx.dataio.gdal.activator.GDALDistributionInstaller;
import org.esa.s2tbx.dataio.gdal.activator.GDALInstallInfo;
import org.esa.snap.core.dataio.EncodeQualification;
import org.esa.snap.core.dataio.ProductIOPlugInManager;
import org.esa.snap.core.dataio.ProductWriterPlugIn;
import org.esa.snap.core.datamodel.Product;
import org.esa.snap.core.util.io.SnapFileFilter;
import org.junit.Before;
import org.junit.Test;

import javax.media.jai.JAI;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Jean Coravu
 */
public abstract class AbstractTestDriverProductWriterPlugIn {
    private final AbstractDriverProductWriterPlugIn writerPlugIn;
    private final String driverName;

    protected AbstractTestDriverProductWriterPlugIn(String driverName, AbstractDriverProductWriterPlugIn writerPlugIn) {
        this.driverName = driverName;
        this.writerPlugIn = writerPlugIn;
    }

    @Before
    public final void setUp() throws Exception {
        if (!GDALInstallInfo.INSTANCE.isPresent()) {
            GDALDistributionInstaller.install();
        }
    }

    @Test
    public void testPluginIsLoaded() {
        if (GDALInstallInfo.INSTANCE.isPresent()) {
            String formatNamesToCheck = getFormatNameToCheck();
            Iterator<ProductWriterPlugIn> iterator = ProductIOPlugInManager.getInstance().getWriterPlugIns(formatNamesToCheck);
            assertTrue(iterator.hasNext());

            ProductWriterPlugIn loadedPlugIn = iterator.next();
            assertEquals(this.writerPlugIn.getClass(), loadedPlugIn.getClass());

            assertTrue(!iterator.hasNext());
        }
    }

    @Test
    public void testFormatNames() {
        if (GDALInstallInfo.INSTANCE.isPresent()) {
            String[] formatNames = this.writerPlugIn.getFormatNames();
            assertNotNull(formatNames);
            assertEquals(1, formatNames.length);

            String formatNamesToCheck = getFormatNameToCheck();
            assertEquals(formatNamesToCheck, formatNames[0]);
        }
    }

    @Test
    public void testOutputTypes() {
        if (GDALInstallInfo.INSTANCE.isPresent()) {
            Class[] classes = this.writerPlugIn.getOutputTypes();
            assertNotNull(classes);
            assertEquals(2, classes.length);

            List<Class> list = Arrays.asList(classes);
            assertEquals(true, list.contains(File.class));
            assertEquals(true, list.contains(String.class));
        }
    }

    @Test
    public void testProductFileFilter() {
        if (GDALInstallInfo.INSTANCE.isPresent()) {
            SnapFileFilter snapFileFilter = this.writerPlugIn.getProductFileFilter();
            assertNull(snapFileFilter);
        }
    }

    @Test
    public void testEncodingQualificationWithNullProduct() throws Exception {
        if (GDALInstallInfo.INSTANCE.isPresent()) {
            EncodeQualification encodeQualification = this.writerPlugIn.getEncodeQualification(null);
            assertNotNull(encodeQualification);
            assertEquals(EncodeQualification.Preservation.FULL, encodeQualification.getPreservation());
        }
    }

    @Test
    public void testEncodingQualificationWithNonNullProduct() throws Exception {
        if (GDALInstallInfo.INSTANCE.isPresent()) {
            Product product = new Product("tempProduct", getFormatNameToCheck(), 20, 30);
            product.setPreferredTileSize(JAI.getDefaultTileSize());
            EncodeQualification encodeQualification = this.writerPlugIn.getEncodeQualification(product);
            assertNotNull(encodeQualification);
            assertEquals(EncodeQualification.Preservation.FULL, encodeQualification.getPreservation());
        }
    }

    private String getFormatNameToCheck() {
        return "GDAL-" + this.driverName + "-WRITER";
    }
}
