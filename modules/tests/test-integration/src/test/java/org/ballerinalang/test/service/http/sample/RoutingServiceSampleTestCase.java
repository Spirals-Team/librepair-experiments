/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/
package org.ballerinalang.test.service.http.sample;

import org.ballerinalang.test.IntegrationTestCase;
import org.ballerinalang.test.context.Constant;
import org.ballerinalang.test.context.ServerInstance;
import org.ballerinalang.test.util.HttpClientRequest;
import org.ballerinalang.test.util.HttpResponse;
import org.ballerinalang.test.util.TestConstant;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Testing the Routing service sample located in
 * ballerina_home/samples/routingServices/routingServices.bal.
 */
public class RoutingServiceSampleTestCase extends IntegrationTestCase {
    private final String requestNyseMessage = "{\"name\":\"nyse\"}";
    private final String responseNyseMessage = "{\"exchange\":\"nyse\",\"name\":\"IBM\",\"value\":\"127.50\"}";
    private final String requestNasdaqMessage = "{\"name\":\"nasdaq\"}";
    private final String responseNasdaqMessage = "{\"exchange\":\"nasdaq\",\"name\":\"IBM\",\"value\":\"127.50\"}";

    private ServerInstance ballerinaServer;

    @BeforeClass
    private void setup() throws Exception {
        ballerinaServer = ServerInstance.initBallerinaServer();
        String serviceSampleDir = ballerinaServer.getServerHome() + File.separator + Constant.SERVICE_SAMPLE_DIR;
        String balFile = serviceSampleDir + File.separator + "routingServices"
                + File.separator + "routingServices.balx";
        ballerinaServer.startBallerinaServer(balFile);
    }

    @Test(description = "Test Content base routing sample")
    public void testContentBaseRouting() throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put(TestConstant.HEADER_CONTENT_TYPE, TestConstant.CONTENT_TYPE_JSON);
        //sending nyse as name
        HttpResponse response = HttpClientRequest.doPost(ballerinaServer
                .getServiceURLHttp("cbr"), requestNyseMessage, headers);
        Assert.assertEquals(response.getResponseCode(), 200, "Response code mismatched");
        Assert.assertEquals(response.getHeaders().get(TestConstant.HEADER_CONTENT_TYPE)
                , TestConstant.CONTENT_TYPE_JSON, "Content-Type mismatched");
        Assert.assertEquals(response.getData(), responseNyseMessage, "Message content mismatched. " +
                                                                     "Routing failed for nyse");

        //sending nasdaq as name
        response = HttpClientRequest.doPost(ballerinaServer
                .getServiceURLHttp("cbr"), requestNasdaqMessage, headers);
        Assert.assertEquals(response.getResponseCode(), 200, "Response code mismatched");
        Assert.assertEquals(response.getHeaders().get(TestConstant.HEADER_CONTENT_TYPE)
                , TestConstant.CONTENT_TYPE_JSON, "Content-Type mismatched");
        Assert.assertEquals(response.getData(), responseNasdaqMessage, "Message content mismatched. " +
                                                                       "Routing failed for nasdaq");
    }

    @Test(description = "Test Header base routing sample")
    public void testHeaderBaseRouting() throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put(TestConstant.HEADER_CONTENT_TYPE, TestConstant.CONTENT_TYPE_JSON);
        //sending nyse as name header
        headers.put("name", "nyse");
        HttpResponse response = HttpClientRequest.doGet(ballerinaServer
                .getServiceURLHttp("hbr"), headers);
        Assert.assertEquals(response.getResponseCode(), 200, "Response code mismatched");
        Assert.assertEquals(response.getHeaders().get(TestConstant.HEADER_CONTENT_TYPE)
                , TestConstant.CONTENT_TYPE_JSON, "Content-Type mismatched");
        Assert.assertEquals(response.getData(), responseNyseMessage
                , "Message content mismatched. Routing failed for nyse");

        //sending nasdaq as http header
        headers.put("name", "nasdaq");
        response = HttpClientRequest.doGet(ballerinaServer
                .getServiceURLHttp("hbr"), headers);
        Assert.assertEquals(response.getResponseCode(), 200, "Response code mismatched");
        Assert.assertEquals(response.getHeaders().get(TestConstant.HEADER_CONTENT_TYPE)
                , TestConstant.CONTENT_TYPE_JSON, "Content-Type mismatched");
        Assert.assertEquals(response.getData(), responseNasdaqMessage
                , "Message content mismatched. Routing failed for nasdaq");
    }

    @AfterClass
    private void cleanup() throws Exception {
        ballerinaServer.stopServer();
    }
}
