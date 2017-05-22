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
package org.apache.nifi.processors.aws.credentials.provider.service;

import static org.junit.Assert.assertEquals;

import org.apache.nifi.processors.aws.AbstractAWSProcessor;
import org.apache.nifi.processors.aws.s3.FetchS3Object;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.internal.StaticCredentialsProvider;

public class AWSCredentialsProviderControllerServiceTest {

    @Test
    public void testDefaultAWSCredentialsProviderChain() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);

        runner.enableControllerService(serviceImpl);

        runner.assertValid(serviceImpl);
        final AWSCredentialsProviderService service = (AWSCredentialsProviderService) runner.getProcessContext()
                .getControllerServiceLookup().getControllerService("awsCredentialsProvider");
        Assert.assertNotNull(service);
        final AWSCredentialsProvider credentialsProvider = service.getCredentialsProvider();
        Assert.assertNotNull(credentialsProvider);
        assertEquals("credentials provider should be equal", DefaultAWSCredentialsProviderChain.class,
                credentialsProvider.getClass());
    }

    @Test
    public void testKeysCredentialsProvider() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.enableControllerService(serviceImpl);

        runner.assertValid(serviceImpl);
        final AWSCredentialsProviderService service = (AWSCredentialsProviderService) runner.getProcessContext()
                .getControllerServiceLookup().getControllerService("awsCredentialsProvider");
        Assert.assertNotNull(service);
        final AWSCredentialsProvider credentialsProvider = service.getCredentialsProvider();
        Assert.assertNotNull(credentialsProvider);
        assertEquals("credentials provider should be equal", StaticCredentialsProvider.class,
                credentialsProvider.getClass());
    }

    @Test
    public void testKeysCredentialsProviderWithRoleAndName() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_ARN, "Role");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_NAME, "RoleName");
        runner.enableControllerService(serviceImpl);

        runner.assertValid(serviceImpl);
        final AWSCredentialsProviderService service = (AWSCredentialsProviderService) runner.getProcessContext()
                .getControllerServiceLookup().getControllerService("awsCredentialsProvider");
        Assert.assertNotNull(service);
        final AWSCredentialsProvider credentialsProvider = service.getCredentialsProvider();
        Assert.assertNotNull(credentialsProvider);
        assertEquals("credentials provider should be equal", STSAssumeRoleSessionCredentialsProvider.class,
                credentialsProvider.getClass());
    }

    @Test
    public void testKeysCredentialsProviderWithRoleAndNameAndSessionTimeoutInRange() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_ARN, "Role");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_NAME, "RoleName");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.MAX_SESSION_TIME, "1000");
        runner.enableControllerService(serviceImpl);

        runner.assertValid(serviceImpl);
        final AWSCredentialsProviderService service = (AWSCredentialsProviderService) runner.getProcessContext()
                .getControllerServiceLookup().getControllerService("awsCredentialsProvider");
        Assert.assertNotNull(service);
        final AWSCredentialsProvider credentialsProvider = service.getCredentialsProvider();
        Assert.assertNotNull(credentialsProvider);
        assertEquals("credentials provider should be equal", STSAssumeRoleSessionCredentialsProvider.class,
                credentialsProvider.getClass());
    }

    @Test
    public void testKeysCredentialsProviderWithRoleAndNameAndSessionTimeout900() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_ARN, "Role");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_NAME, "RoleName");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.MAX_SESSION_TIME, "900");
        runner.enableControllerService(serviceImpl);

        runner.assertValid(serviceImpl);
    }

    @Test
    public void testKeysCredentialsProviderWithRoleAndNameAndSessionTimeout3600() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_ARN, "Role");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_NAME, "RoleName");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.MAX_SESSION_TIME, "900");
        runner.enableControllerService(serviceImpl);

        runner.assertValid(serviceImpl);
    }

    @Test(expected = AssertionError.class)
    public void testKeysCredentialsProviderWithRoleAndNameAndSessionTimeoutLessThan900() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_ARN, "Role");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_NAME, "RoleName");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.MAX_SESSION_TIME, "899");
        runner.enableControllerService(serviceImpl);
        runner.assertNotValid(serviceImpl);
    }

    @Test(expected = AssertionError.class)
    public void testKeysCredentialsProviderWithRoleAndNameAndSessionTimeoutGreaterThan3600() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_ARN, "Role");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_NAME, "RoleName");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.MAX_SESSION_TIME, "899");
        runner.enableControllerService(serviceImpl);
    }

    @Test
    public void testKeysCredentialsProviderWithRoleOnlyInvalid() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_ARN, "Role");
        runner.enableControllerService(serviceImpl);

        runner.assertNotValid(serviceImpl);
    }

    @Test
    public void testKeysCredentialsProviderWithRoleNameOnlyInvalid() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_NAME, "RoleName");
        runner.enableControllerService(serviceImpl);

        runner.assertNotValid(serviceImpl);
    }

    @Test
    public void testFileCredentialsProviderWithRole() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.CREDENTIALS_FILE,
                "src/test/resources/mock-aws-credentials.properties");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_ARN, "Role");
        runner.setProperty(serviceImpl, AWSCredentialsProviderControllerService.ASSUME_ROLE_NAME, "RoleName");
        runner.enableControllerService(serviceImpl);

        runner.assertValid(serviceImpl);
        final AWSCredentialsProviderService service = (AWSCredentialsProviderService) runner.getProcessContext()
                .getControllerServiceLookup().getControllerService("awsCredentialsProvider");
        Assert.assertNotNull(service);
        final AWSCredentialsProvider credentialsProvider = service.getCredentialsProvider();
        Assert.assertNotNull(credentialsProvider);
        assertEquals("credentials provider should be equal", STSAssumeRoleSessionCredentialsProvider.class,
                credentialsProvider.getClass());
    }

    @Test
    public void testFileCredentialsProvider() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.CREDENTIALS_FILE,
                "src/test/resources/mock-aws-credentials.properties");
        runner.enableControllerService(serviceImpl);

        runner.assertValid(serviceImpl);
        final AWSCredentialsProviderService service = (AWSCredentialsProviderService) runner.getProcessContext()
                .getControllerServiceLookup().getControllerService("awsCredentialsProvider");
        Assert.assertNotNull(service);
        final AWSCredentialsProvider credentialsProvider = service.getCredentialsProvider();
        Assert.assertNotNull(credentialsProvider);
        assertEquals("credentials provider should be equal", PropertiesFileCredentialsProvider.class,
                credentialsProvider.getClass());
    }

    @Test
    public void testFileCredentialsProviderBadFile() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.CREDENTIALS_FILE,
                "src/test/resources/bad-mock-aws-credentials.properties");
        runner.enableControllerService(serviceImpl);

        runner.assertNotValid(serviceImpl);
    }

    @Test
    public void testFileAndAccessSecretKeyInvalid() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.CREDENTIALS_FILE,
                "src/test/resources/mock-aws-credentials.properties");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.enableControllerService(serviceImpl);

        runner.assertNotValid(serviceImpl);
    }

    @Test
    public void testFileAndAccessKeyInvalid() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.CREDENTIALS_FILE,
                "src/test/resources/mock-aws-credentials.properties");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.enableControllerService(serviceImpl);

        runner.assertNotValid(serviceImpl);
    }

    @Test
    public void testFileAndSecretKeyInvalid() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.CREDENTIALS_FILE,
                "src/test/resources/mock-aws-credentials.properties");
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.enableControllerService(serviceImpl);

        runner.assertNotValid(serviceImpl);
    }

    @Test
    public void testAccessKeyOnlyInvalid() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.ACCESS_KEY, "awsAccessKey");
        runner.enableControllerService(serviceImpl);

        runner.assertNotValid(serviceImpl);
    }

    @Test
    public void testSecretKeyOnlyInvalid() throws Throwable {
        final TestRunner runner = TestRunners.newTestRunner(FetchS3Object.class);
        final AWSCredentialsProviderControllerService serviceImpl = new AWSCredentialsProviderControllerService();
        runner.addControllerService("awsCredentialsProvider", serviceImpl);
        runner.setProperty(serviceImpl, AbstractAWSProcessor.SECRET_KEY, "awsSecretKey");
        runner.enableControllerService(serviceImpl);

        runner.assertNotValid(serviceImpl);
    }
}
