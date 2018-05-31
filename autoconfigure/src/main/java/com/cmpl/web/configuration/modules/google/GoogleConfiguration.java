package com.cmpl.web.configuration.modules.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.cmpl.web.google.DoNothingDriveAdapter;
import com.cmpl.web.google.DriveAdapter;
import com.cmpl.web.google.DriveAdapterImpl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

@Configuration
@PropertySource("classpath:/google/google.properties")
public class GoogleConfiguration {

  @Value("${credentialsDirectory}")
  String credentialsDirectory;

  @Value("${applicationName}")
  String applicationName;

  @Value("${clientSecret}")
  String clientSecret;

  @Bean
  @ConditionalOnProperty(prefix = "drive.", name = "enabled")
  public JsonFactory jsonFactory() {
    return JacksonFactory.getDefaultInstance();

  }

  @Bean
  @ConditionalOnProperty(prefix = "drive.", name = "enabled")
  public HttpTransport httpTransport() throws GeneralSecurityException, IOException {
    return GoogleNetHttpTransport.newTrustedTransport();

  }

  @Bean
  @ConditionalOnProperty(prefix = "drive.", name = "enabled")
  public FileDataStoreFactory fileDataStoreFactory() throws IOException {
    return new FileDataStoreFactory(new File(credentialsDirectory));
  }

  @Bean
  @ConditionalOnProperty(prefix = "drive.", name = "enabled")
  public Drive driveService(HttpTransport httpTransport, JsonFactory jsonFactory, Credential credential) {
    return new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName(applicationName).build();
  }

  @Bean
  @ConditionalOnProperty(prefix = "drive.", name = "enabled")
  public Credential credential(JsonFactory jsonFactory, HttpTransport httpTransport,
      FileDataStoreFactory fileDataStoreFactory) throws IOException {
    InputStream in = clientSecretInputStream();
    GoogleClientSecrets clientSecrets = googleClientSecrets(jsonFactory, in);

    List<String> scopes = new ArrayList<>();
    scopes.add(DriveScopes.DRIVE);

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory,
        clientSecrets, scopes).setDataStoreFactory(fileDataStoreFactory).setAccessType("offline").build();
    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

  }

  GoogleClientSecrets googleClientSecrets(JsonFactory jsonFactory, InputStream in) throws IOException {
    return GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
  }

  InputStream clientSecretInputStream() throws FileNotFoundException {
    File clientSecretJson = new File(clientSecret);
    return new FileInputStream(clientSecretJson);
  }

  @Bean
  @ConditionalOnProperty(prefix = "drive.", name = "enabled")
  DriveAdapter driveAdapter(Drive driveService) {
    return new DriveAdapterImpl(driveService);
  }

  @Bean
  @ConditionalOnProperty(prefix = "drive.", name = "enabled", havingValue = "false")
  DriveAdapter mockDriveAdapter() {
    return new DoNothingDriveAdapter();
  }

}
