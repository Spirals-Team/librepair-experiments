package com.weaverplatform.sdk;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.Test;

import java.io.InputStreamReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by bastbijl on 27/11/2017
 */
public class ProjectSnapshotTest {
  private static final Gson converter = new Gson();


  @Test
  public void testVersion() {

    Weaver w = TestSuite.getInstance();

    assertNotNull(w.getVersion());
    w.wipe();
  }

  static String convertStreamToString(java.io.InputStream is) {
    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }

  @Test
  public void testSnapshot() {

    JsonArray json = converter.fromJson(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("graphs.json")), JsonArray.class);

    Weaver w = TestSuite.getInstance();
    w.sendCreate(json, false);

    String writeOpsJson = convertStreamToString(w.getSnapshot(false));
    System.out.println(writeOpsJson);
    assertThat(writeOpsJson, RegexMatcher.matches(".*\"id\":\"coins-base:a\".*"));
    assertThat(writeOpsJson, RegexMatcher.matches(".*\"id\":\"coins-base:b\",\"graph\":\"coins-extra\".*"));
    assertThat(writeOpsJson, RegexMatcher.matches(".*\"id\":\"cjai2y6f87su7mj74wwkxx5xy\",\"graph\":\"otl\".*"));
    assertEquals(417, writeOpsJson.length());

    w.wipe();
  }

  @Test
  public void testSnapshotZipped() {

    JsonArray json = converter.fromJson(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("graphs.json")), JsonArray.class);

    Weaver w = TestSuite.getInstance();
    w.sendCreate(json, false);

    String gzFileJson = convertStreamToString(w.getSnapshot(true));
    w.wipe();

    System.out.println(gzFileJson);
    assertThat(gzFileJson, RegexMatcher.matches("^\\{\"id\":\"\\w*\",\"name\":\"\\w*.gz\"\\}$"));
  }

  @Test
  public void testGraphSnapshot() {

    JsonArray json = converter.fromJson(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("graphs.json")), JsonArray.class);

    Weaver w = TestSuite.getInstance();
    w.sendCreate(json, false);

    String defaultGraphJson = convertStreamToString(w.getSnapshotGraph(null, false));
    String extraGraphJson = convertStreamToString(w.getSnapshotGraph("coins-extra", false));

    w.wipe();

    System.out.println(defaultGraphJson);
    assertThat(defaultGraphJson, RegexMatcher.matches(".*\"id\":\"coins-base:a\".*"));
    assertEquals(defaultGraphJson.length(), 86);

    System.out.println(extraGraphJson);
    assertThat(extraGraphJson, RegexMatcher.matches(".*\"id\":\"coins-base:b\",\"graph\":\"coins-extra\".*"));
    assertEquals(extraGraphJson.length(), 108);
  }

  @Test
  public void testGraphSnapshotZipped() {

    JsonArray json = converter.fromJson(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("graphs.json")), JsonArray.class);

    Weaver w = TestSuite.getInstance();
    w.sendCreate(json, false);

    String gzFileJson = convertStreamToString(w.getSnapshotGraph("coins-base", true));
    w.wipe();

    System.out.println(gzFileJson);
    assertThat(gzFileJson, RegexMatcher.matches("^\\{\"id\":\"\\w*\",\"name\":\"\\w*.gz\"\\}$"));

  }
}
