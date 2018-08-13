/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.registry.support;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.registry.NotifyListener;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * AbstractRegistryTest
 */
public class AbstractRegistryTest {

    private URL testUrl, testUrl2;

    private NotifyListener listener;
    private AbstractRegistry abstractRegistry;
    private boolean notifySuccess;

    @Before
    public void init() {
        URL url = URL.valueOf("dubbo://" + NetUtils.getLocalAddress().getHostName() + ":2233");
        testUrl = URL.valueOf("http://1.2.3.4:9090/registry?check=false&file=N/A&interface=com.test");
        testUrl2 = URL.valueOf("http://1.2.3.4:9090/registry?check=false&file=N/A&interface=com.test2");

        // test uncreatable file
        try {
            File nonExistDir = File.createTempFile("non", "tmp");
            abstractRegistry = new AbstractRegistry(
                    URL.valueOf(url.getAbsolutePath() + "/?file="
                            + nonExistDir + File.separator + "impossible-folder" + File.separator + "impossible-file")) {
                @Override
                public boolean isAvailable() {
                    return false;
                }
            };
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }

        //init the object
        abstractRegistry = new AbstractRegistry(url) {
            @Override
            public boolean isAvailable() {
                return false;
            }
        };
        //init notify listener
        listener = urls -> notifySuccess = true;
        //notify flag
        notifySuccess = false;
    }

    @Test
    public void registerTest() {
        //check parameters
        try {
            abstractRegistry.register(null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        // check if register successfully
        int beginSize = abstractRegistry.getRegistered().size();
        abstractRegistry.register(testUrl);
        Assert.assertEquals(beginSize + 1, abstractRegistry.getRegistered().size());
        //check register when the url is the same
        abstractRegistry.register(testUrl);
        Assert.assertEquals(beginSize + 1, abstractRegistry.getRegistered().size());
    }

    @Test
    public void unregisterTest() {
        //check parameters
        try {
            abstractRegistry.unregister(null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        // check if unregister url successfully
        abstractRegistry.register(testUrl);
        int beginSize = abstractRegistry.getRegistered().size();
        abstractRegistry.unregister(testUrl);
        Assert.assertEquals(beginSize - 1, abstractRegistry.getRegistered().size());
        // check if unregister a not exist url successfully
        abstractRegistry.unregister(testUrl);
        Assert.assertEquals(beginSize - 1, abstractRegistry.getRegistered().size());
    }

    @Test
    public void subscribeTest() {
        //check parameters
        try {
            abstractRegistry.subscribe(null, listener);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        //check parameters
        try {
            abstractRegistry.subscribe(testUrl, null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        //check parameters
        try {
            abstractRegistry.subscribe(null, null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        // check if subscribe successfully
        abstractRegistry.subscribe(testUrl, listener);
        Assert.assertNotNull(abstractRegistry.getSubscribed().get(testUrl));
        Assert.assertTrue(abstractRegistry.getSubscribed().get(testUrl).contains(listener));
    }

    @Test
    public void unsubscribeTest() {
        //check parameters
        try {
            abstractRegistry.unsubscribe(null, listener);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        //check parameters
        try {
            abstractRegistry.unsubscribe(testUrl, null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        //check parameters
        try {
            abstractRegistry.unsubscribe(null, null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        // check if unsubscribe successfully
        abstractRegistry.subscribe(testUrl, listener);
        abstractRegistry.unsubscribe(testUrl, listener);
        Assert.assertFalse(abstractRegistry.getSubscribed().get(testUrl).contains(listener));
    }

    @Test
    public void recoverTest() throws Exception {
        abstractRegistry.register(testUrl);
        abstractRegistry.subscribe(testUrl, listener);
        abstractRegistry.recover();
        // check if recover successfully
        Assert.assertTrue(abstractRegistry.getRegistered().contains(testUrl));
        Assert.assertNotNull(abstractRegistry.getSubscribed().get(testUrl));
        Assert.assertTrue(abstractRegistry.getSubscribed().get(testUrl).contains(listener));
    }

    @Test
    public void notifyTest() {
        abstractRegistry.subscribe(testUrl, listener);
        abstractRegistry.subscribe(testUrl2, listener);
        List<URL> urls = new ArrayList<>();
        urls.add(testUrl);
        urls.add(testUrl2);
        // check if notify successfully
        Assert.assertFalse(notifySuccess);
        Assert.assertEquals(0, abstractRegistry.lookup(testUrl2).size());
        abstractRegistry.notify(urls);
        Assert.assertEquals(1, abstractRegistry.lookup(testUrl).size());
        Assert.assertTrue(notifySuccess);
    }

    @Test
    public void notify2Test() {
        //check parameters
        try {
            abstractRegistry.notify(null, null, null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        //check parameters
        try {
            abstractRegistry.notify(testUrl, null, null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }

        List<URL> urls = new ArrayList<>();
        // try empty urls
        abstractRegistry.notify(testUrl, listener, urls);
        urls.add(testUrl);
        // check if notify successfully
        Assert.assertFalse(notifySuccess);
        abstractRegistry.notify(testUrl, listener, urls);
        Assert.assertTrue(notifySuccess);
        urls.add(testUrl2);
        // additional url added, test notifying with cache
        abstractRegistry.notify(testUrl, listener, urls);
        // no-cache for another url
        abstractRegistry.notify(testUrl2, listener, urls);
        Assert.assertTrue(notifySuccess);
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals(abstractRegistry.toString(), "dubbo://wentaodeMacBook-Air.local:2233");
    }

    @Test
    public void getCacheFileTest() {
        Assert.assertNotNull(abstractRegistry.getCacheFile());
    }

    @Test
    public void getCachePropertiesTest() {
        Assert.assertNotNull(abstractRegistry.getCacheProperties());
    }

    @Test
    public void getLastCacheChangedTest() {
        Assert.assertEquals(0, abstractRegistry.getLastCacheChanged().longValue());
    }

    @Test
    public void getCacheUrlsTest() {
        Assert.assertEquals(1, abstractRegistry.getCacheUrls(testUrl).size());
    }

    @After
    public void destroy() {
        abstractRegistry.destroy();
    }
}