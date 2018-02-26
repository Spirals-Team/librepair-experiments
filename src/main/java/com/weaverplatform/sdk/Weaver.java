package com.weaverplatform.sdk;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.file.StreamDataBodyPart;
import com.weaverplatform.CliOptions;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * @author bastbijl, Sysunite 2017
 */
public class Weaver {
  public static int MAX_OPERATIONS = 5000;
  private static enum OP { get, post };
  private CliOptions options;

  public Weaver() {
    this.options = new CliOptions(new String[0]);
  }

  public Weaver(CliOptions options) {
    this.options = options;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  private String uri = "http://localhost:9487";
  private String username = "admin";
  private String password = "admin";
  private String project = null;
  private String authToken = null;

  private Gson converter = new Gson();

  public String login() {
    if(authToken != null) {
      return authToken;
    } else {
      // Sign in
      JsonObject signIn = new JsonObject();
      signIn.addProperty("username", username);
      signIn.addProperty("password", password);

      ClientResponse response = send(signIn, "/user/signInUsername", OP.post);

      authToken = response.getEntity(String.class);
      return authToken;
    }
  }

  public ClientResponse sendProject(JsonObject payload, String target, OP op) {
    payload.addProperty("target", project);
    return sendAuthenticated(payload, target, op);
  }

  public ClientResponse sendAuthenticated(JsonObject payload, String target, OP op) {
    String authToken = login();
    payload.addProperty("authToken", authToken);
    return send(payload, target, op);
  }

  public ClientResponse send(JsonObject payload, String target, OP op) {
    Client client = Client.create();

    WebResource webResource = client.resource(uri + target);
    ClientResponse soapResponse;
    switch (op) {
      case get:
        try {
          String json = URLEncoder.encode(converter.toJson(payload), "UTF-8");
          webResource = webResource.queryParam("payload", json);
        } catch (UnsupportedEncodingException e) {
          throw new RuntimeException(e);
        }
        soapResponse = webResource.type("application/json; charset=utf-8").get(ClientResponse.class);
        break;
      case post:
      default:
        soapResponse = webResource.type("application/json; charset=utf-8").post(ClientResponse.class, converter.toJson(payload));
    }

    // Send response
    if (soapResponse.getStatus() != 200) {
      String response = soapResponse.getEntity(String.class);
      throw new RuntimeException("Message delivery failed on " + target + " response: " + response);
    }
    return soapResponse;
  }

  public ClientResponse sendMultipart(InputStream fileStream, String fileName, String target) {
    Client client = Client.create();
    String authToken = login();

    WebResource webResource = client.resource(uri + target);
//    webResource.header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);

    StreamDataBodyPart fileDataBodyPart = new StreamDataBodyPart("file", fileStream, fileName, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    fileDataBodyPart.setContentDisposition(FormDataContentDisposition.name("file").fileName(fileName).build());

    final MultiPart multiPart = new FormDataMultiPart()
    .field("authToken", authToken, MediaType.TEXT_PLAIN_TYPE)
    .field("target", project, MediaType.TEXT_PLAIN_TYPE)
    .field("filename", fileName, MediaType.TEXT_PLAIN_TYPE)
    .bodyPart(fileDataBodyPart);
    multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
//    multiPart.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);

    return webResource.type("multipart/form-data").post(ClientResponse.class, multiPart);
  }





  public void createProject(String id) {
    JsonObject payload = new JsonObject();
    payload.addProperty("id", id);
    payload.addProperty("name", id);
    sendAuthenticated(payload, "/project/create", OP.post);
  }

  public String projectReady(String id) {
    JsonObject payload = new JsonObject();
    payload.addProperty("id", id);
    return sendAuthenticated(payload, "/project/ready", OP.post).getEntity(String.class);
  }

  public String getVersion() {
    JsonObject payload = new JsonObject();
    return sendProject(payload, "/application/version", OP.get).getEntity(String.class);
  }

  public void wipe() {
    JsonObject payload = new JsonObject();
    sendProject(payload, "/project/wipe", OP.post);
  }

  public void sendCreate(JsonArray operations, boolean quick) {
    JsonArray array = new JsonArray();
    boolean stop = false;
    while(array.size() < MAX_OPERATIONS && operations.size() > 0 && !stop) {
      JsonObject peek = operations.get(0).getAsJsonObject();
      if(peek.get("action").getAsString().equals("increment-attribute")) {
        stop = true;
        if(array.size() == 0) {
          array.add(operations.remove(0));
        }
      } else {
        array.add(operations.remove(0));
      }
    }
    if(operations.size() > 0) {
      System.out.println("will send " + array.size() + " (" + operations.size() + " to go)");
    } else {
      System.out.println("will send " + array.size());
    }
    reallySendCreate(array, quick);
    if(operations.size() > 0) {
      sendCreate(operations, quick);
    }
  }

  public void reallySendCreate(JsonArray operations, boolean quick) {
    JsonObject payload = new JsonObject();
    payload.add("operations", operations);

    // Send the post
    String target = "/write";
    if(quick) {
      target = "/write/quick";
    }
    sendProject(payload, target, OP.post);
  }

  public JsonObject createNode(String id) {
    JsonObject payload = new JsonObject();
    payload.addProperty("timestamp", System.currentTimeMillis());
    payload.addProperty("action", "create-node");
    payload.addProperty("id", id);
    return payload;
  }

  public JsonObject createAttribute(String id, String key, String value) {

    JsonObject payload = new JsonObject();
    payload.addProperty("timestamp", System.currentTimeMillis());
    payload.addProperty("action", "create-attribute");
    payload.addProperty("id", id);
    payload.addProperty("key", key);
    payload.addProperty("value", value);
    return payload;
  }

  public JsonObject createRelation(String from, String key, String to, String id) {

    JsonObject payload = new JsonObject();
    payload.addProperty("timestamp", System.currentTimeMillis());
    payload.addProperty("action", "create-relation");
    payload.addProperty("from", from);
    payload.addProperty("key", key);
    payload.addProperty("to", to);
    payload.addProperty("id", id);
    return payload;
  }

  public Project[] getProjects() {
    ClientResponse res = sendAuthenticated(new JsonObject(), "/project", OP.get);
    String s = res.getEntity(String.class);
    return converter.fromJson(s, Project[].class);
  }

  public String getSnapshot(boolean zipped) {
    JsonObject payload = new JsonObject();
    payload.addProperty("zipped", zipped);
    ClientResponse soapResponse = sendProject(payload, "/snapshot", OP.get);
    return soapResponse.getEntity(String.class);
  }

  public String getSnapshotGraph(String graph, boolean zipped) {
    JsonObject payload = new JsonObject();
    if(graph != null) {
      payload.addProperty("graph", graph);
    }
    payload.addProperty("zipped", zipped);
    ClientResponse soapResponse = sendProject(payload, "/snapshotGraph", OP.get);
    return soapResponse.getEntity(String.class);
  }

  public String getHistory() {
    ClientResponse soapResponse = sendProject(new JsonObject(), "/history", OP.get);
    return soapResponse.getEntity(String.class);
  }

  public void restore(InputStream operationsStream) {
    JsonArray ops = converter.fromJson(new InputStreamReader(operationsStream), JsonArray.class);
    System.out.println("Going to write " + ops.size() + " operations.");
    sendCreate(ops, false);
  }

  public MinioFile[] listFiles() {
    JsonObject payload = new JsonObject();
    ClientResponse soapResponse = sendProject(payload, "/file/list", OP.get);
    String s = soapResponse.getEntity(String.class);
    return converter.fromJson(s, MinioFile[].class);
  }

  public File uploadFile(InputStream fileStream, String fileName) {
    ClientResponse soapResponse = sendMultipart(fileStream, fileName, "/file/upload");

    return converter.fromJson(soapResponse.getEntity(String.class), File.class);
  }

  public InputStream downloadFile(String id) throws IOException {
    JsonObject payload = new JsonObject();
    payload.addProperty("fileId", id);
    ClientResponse soapResponse = sendProject(payload, "/file/download", OP.get);
    return soapResponse.getEntityInputStream();
  }

  public String deleteFile(String id) {
    JsonObject payload = new JsonObject();
    payload.addProperty("fileId", id);
    ClientResponse soapResponse = sendProject(payload, "/file/delete", OP.post);
    return soapResponse.getEntity(String.class);
  }


}
