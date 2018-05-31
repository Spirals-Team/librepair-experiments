package com.cmpl.web.configuration.modules.google;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;

@RunWith(MockitoJUnitRunner.class)
public class GoogleConfigurationTest {

  @Spy
  private GoogleConfiguration configuration;

  @Test
  public void testJsonFactory() throws Exception {
    Assert.assertEquals(JacksonFactory.getDefaultInstance(), configuration.jsonFactory());
  }

  @Test
  public void testHttpTransport() throws Exception {
    Assert.assertEquals(NetHttpTransport.class, configuration.httpTransport().getClass());
  }

  @Test
  public void testFileDataStoreFactory() throws Exception {
    configuration.credentialsDirectory = "test";
    Assert.assertEquals(FileDataStoreFactory.class, configuration.fileDataStoreFactory().getClass());
    configuration.credentialsDirectory = null;
  }

  @Test
  public void testDriveService() throws Exception {
    Assert.assertEquals(
        Drive.class,
        configuration.driveService(BDDMockito.mock(HttpTransport.class), BDDMockito.mock(JsonFactory.class),
            BDDMockito.mock(Credential.class)).getClass());
  }

}
