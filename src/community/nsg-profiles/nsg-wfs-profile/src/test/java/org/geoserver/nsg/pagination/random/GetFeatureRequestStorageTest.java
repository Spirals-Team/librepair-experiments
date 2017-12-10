/* (c) 2017 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.nsg.pagination.random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.util.file.File;
import org.geoserver.config.GeoServerDataDirectory;
import org.geoserver.data.test.SystemTestData;
import org.geoserver.platform.GeoServerExtensions;
import org.geoserver.platform.GeoServerResourceLoader;
import org.geoserver.platform.resource.Resource;
import org.geoserver.wfs.v2_0.WFS20TestSupport;
import org.geotools.data.DataStore;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.jdbc.JDBCDataStoreFactory;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.w3c.dom.Document;

public class GetFeatureRequestStorageTest extends WFS20TestSupport {

    @Override
    protected void onTearDown(SystemTestData testData) throws Exception {
        IndexConfiguration ic = applicationContext.getBean(IndexConfiguration.class);
        DataStore dataStore = ic.getCurrentDataStore();
        dataStore.dispose();
        super.onTearDown(testData);
    }

    @Test
    public void testCleanOldRequest() throws Exception {
        Document doc = getAsDOM(
                "ows?service=WFS&version=2.0.0&request=GetFeature&typeNames=cdf:Fifteen&resultType=index");
        String resultSetId = doc.getDocumentElement().getAttribute("resultSetID");
        IndexConfiguration ic = applicationContext.getBean(IndexConfiguration.class);
        DataStore dataStore = ic.getCurrentDataStore();
        SimpleFeatureStore featureStore = (SimpleFeatureStore) dataStore
                .getFeatureSource(IndexInitializer.STORE_SCHEMA_NAME);
        SimpleFeature feature = featureStore.getFeatures(CQL.toFilter("ID='" + resultSetId + "'"))
                .features().next();
        assertNotNull(feature);

        // Change timeout from default to 5 seconds
        GeoServerResourceLoader loader = GeoServerExtensions.bean(GeoServerResourceLoader.class);
        GeoServerDataDirectory dd = new GeoServerDataDirectory(loader);
        Properties properties = new Properties();
        Resource resource = dd
                .get(IndexInitializer.MODULE_DIR + "/" + IndexInitializer.PROPERTY_FILENAME);
        InputStream is = resource.in();
        properties.load(is);
        is.close();
        properties.put("resultSets.timeToLive", "5");
        OutputStream out = resource.out();
        properties.store(out, null);
        out.close();
        final CountDownLatch done1 = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Long ttl = ic.getTimeToLiveInSec();
                        if (ttl == 5) {
                            done1.countDown();
                            break;
                        }
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
        done1.await(100, TimeUnit.SECONDS);
        assertEquals(new Long(5), ic.getTimeToLiveInSec());
        // Check that feature not used is deleted
        final CountDownLatch done2 = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        boolean exists = featureStore
                                .getFeatures(CQL.toFilter("ID='" + resultSetId + "'")).features()
                                .hasNext();
                        if (!exists) {
                            done2.countDown();
                            break;
                        }
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
        done2.await(100, TimeUnit.SECONDS);
        boolean exists = featureStore.getFeatures(CQL.toFilter("ID='" + resultSetId + "'"))
                .features().hasNext();
        assertFalse(exists);
    }

    @Test
    public void testConcurrentChangeDatabaseParameters() throws Exception {
        GeoServerResourceLoader loader = GeoServerExtensions.bean(GeoServerResourceLoader.class);
        GeoServerDataDirectory dd = new GeoServerDataDirectory(loader);
        Resource resource = dd
                .get(IndexInitializer.MODULE_DIR + "/" + IndexInitializer.PROPERTY_FILENAME);
        Properties properties = new Properties();
        InputStream is = resource.in();
        properties.load(is);
        is.close();
        properties.put(IndexInitializer.PROPERTY_DB_PREFIX + JDBCDataStoreFactory.DATABASE.key,
                dd.root().getPath() + "/nsg-profile/db/resultSets2");
        IndexConfiguration ic = applicationContext.getBean(IndexConfiguration.class);
        ExecutorCompletionService<Object> es = new ExecutorCompletionService<>(
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        final int REQUESTS = 100;
        for (int i = 0; i < REQUESTS; i++) {
            int count = i;
            es.submit(() -> {
                getAsDOM(
                        "ows?service=WFS&version=2.0.0&request=GetFeature&typeNames=cdf:Fifteen&resultType=index");
                if (count == REQUESTS / 2) {
                    // Change database
                    OutputStream out = resource.out();
                    properties.store(out, null);
                    out.close();
                }
                return null;
            });
        }
        // just check there are no exceptions
        for (int i = 0; i < REQUESTS; i++) {
            es.take().get();
        }
        // wait for listener receive database notification changes
        File dbDataFile = new File(dd.root().getPath() + "/nsg-profile/db/resultSets2.data.db");
        final CountDownLatch done = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Map<String, Object> params = ic.getCurrentDataStoreParams();
                    boolean condition = (dd.root().getPath() + "/nsg-profile/db/resultSets2")
                            .equals(params.get(JDBCDataStoreFactory.DATABASE.key))
                            && dbDataFile.exists();
                    if (condition) {
                        done.countDown();
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
        done.await(100, TimeUnit.SECONDS);

        DataStore dataStore = ic.getCurrentDataStore();
        assertTrue(dbDataFile.exists());

        Map<String, Object> params = ic.getCurrentDataStoreParams();
        assertEquals(dd.root().getPath() + "/nsg-profile/db/resultSets2",
                params.get(JDBCDataStoreFactory.DATABASE.key));

        SimpleFeatureStore featureStore = (SimpleFeatureStore) dataStore
                .getFeatureSource(IndexInitializer.STORE_SCHEMA_NAME);
        assertEquals(REQUESTS, featureStore.getFeatures().size());
    }

}
