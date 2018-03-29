package com.weaverplatform.sdk;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gijs on 09/06/2017.
 */
public class BatchingTest {
  private static class SendMockedWeaver extends Weaver {
    private List<JsonArray> operations = new ArrayList<>();

    public SendMockedWeaver() {
      super();
    }

    @Override
    public void reallySendCreate(JsonArray operations, boolean quick) {
      this.operations.add(operations);
    }
  }

  private static final Gson converter = new Gson();

  @Test
  public void testNonIncrement() {
    SendMockedWeaver w = new SendMockedWeaver();
    w.sendCreate(converter.fromJson("[ {\"timestamp\":1496070297249,\"action\":\"create-attribute\",\"id\":\"cj3a9mla9000j70p7izegdz8w\",\"key\":\"quantity\",\"value\":0}\n" +
      ", {\"timestamp\":1496070297249,\"action\":\"create-relation\",\"id\":\"cj3a9mla9000k70p7yg1bd2qo\",\"from\":\"cj3a9mla9000j70p7izegdz8w\",\"key\":\"to\",\"to\":\"cj300z2dh001069rzmrbv9nwx\"}\n" +
      ", {\"timestamp\":1496070297249,\"action\":\"create-relation\",\"id\":\"cj3a9mlaa000l70p794jiwout\",\"from\":\"cj300z3yc001269rzfzjpydpv\",\"key\":\"consistsOf\",\"to\":\"cj3a9mla9000j70p7izegdz8w\"}\n" +
      ", {\"timestamp\":1496070299515,\"action\":\"update-attribute\",\"id\":\"cj300z3yc001269rzfzjpydpv\",\"key\":\"orderOfChildren\",\"value\":\"[\\\"cj307cf2s0006ajrzonhwh5us\\\",\\\"cj300z2dh001069rzmrbv9nwx\\\",\\\"cj3016kej001m69rz4t6edgqm\\\",\\\"cj3016o34001r69rzycwiwn73\\\"]\"}]",JsonArray.class), false);
    Assert.assertEquals(1, w.operations.size());
    Assert.assertEquals(4, w.operations.get(0).size());
  }

  @Test
  public void testIncrementAtStart() {
    SendMockedWeaver w = new SendMockedWeaver();
    w.sendCreate(converter.fromJson("[ {\"timestamp\":1496067728063,\"action\":\"increment-attribute\",\"id\":\"testymctest\",\"key\":\"instanceCount\",\"value\":1}" +
      ", {\"timestamp\":1496070297249,\"action\":\"create-attribute\",\"id\":\"cj3a9mla9000j70p7izegdz8w\",\"key\":\"quantity\",\"value\":0}\n" +
      ", {\"timestamp\":1496070297249,\"action\":\"create-relation\",\"id\":\"cj3a9mla9000k70p7yg1bd2qo\",\"from\":\"cj3a9mla9000j70p7izegdz8w\",\"key\":\"to\",\"to\":\"cj300z2dh001069rzmrbv9nwx\"}\n" +
      ", {\"timestamp\":1496070297249,\"action\":\"create-relation\",\"id\":\"cj3a9mlaa000l70p794jiwout\",\"from\":\"cj300z3yc001269rzfzjpydpv\",\"key\":\"consistsOf\",\"to\":\"cj3a9mla9000j70p7izegdz8w\"}\n" +
      ", {\"timestamp\":1496070299515,\"action\":\"update-attribute\",\"id\":\"cj300z3yc001269rzfzjpydpv\",\"key\":\"orderOfChildren\",\"value\":\"[\\\"cj307cf2s0006ajrzonhwh5us\\\",\\\"cj300z2dh001069rzmrbv9nwx\\\",\\\"cj3016kej001m69rz4t6edgqm\\\",\\\"cj3016o34001r69rzycwiwn73\\\"]\"}]",JsonArray.class), false);
    Assert.assertEquals("Expected number of batches", 2, w.operations.size());
    Assert.assertEquals("Single increment batch",1, w.operations.get(0).size());
    Assert.assertEquals("Other ops batch",4, w.operations.get(1).size());
  }

  @Test
  public void testIncrementAtEnd() {
    SendMockedWeaver w = new SendMockedWeaver();
    w.sendCreate(converter.fromJson("[ {\"timestamp\":1496067728063,\"action\":\"increment-attribute\",\"id\":\"testymctest\",\"key\":\"instanceCount\",\"value\":1}" +
      ", {\"timestamp\":1496070297249,\"action\":\"create-attribute\",\"id\":\"cj3a9mla9000j70p7izegdz8w\",\"key\":\"quantity\",\"value\":0}\n" +
      ", {\"timestamp\":1496070297249,\"action\":\"create-relation\",\"id\":\"cj3a9mla9000k70p7yg1bd2qo\",\"from\":\"cj3a9mla9000j70p7izegdz8w\",\"key\":\"to\",\"to\":\"cj300z2dh001069rzmrbv9nwx\"}\n" +
      ", {\"timestamp\":1496070297249,\"action\":\"create-relation\",\"id\":\"cj3a9mlaa000l70p794jiwout\",\"from\":\"cj300z3yc001269rzfzjpydpv\",\"key\":\"consistsOf\",\"to\":\"cj3a9mla9000j70p7izegdz8w\"}\n" +
      ", {\"timestamp\":1496070299515,\"action\":\"update-attribute\",\"id\":\"cj300z3yc001269rzfzjpydpv\",\"key\":\"orderOfChildren\",\"value\":\"[\\\"cj307cf2s0006ajrzonhwh5us\\\",\\\"cj300z2dh001069rzmrbv9nwx\\\",\\\"cj3016kej001m69rz4t6edgqm\\\",\\\"cj3016o34001r69rzycwiwn73\\\"]\"}" +
      ", {\"timestamp\":1496067728063,\"action\":\"increment-attribute\",\"id\":\"testymctest\",\"key\":\"instanceCount\",\"value\":1}" +
      "]",JsonArray.class), false);
    Assert.assertEquals("Expected number of batches", 3, w.operations.size());
    Assert.assertEquals("Single increment batch",1, w.operations.get(0).size());
    Assert.assertEquals("Other ops batch",4, w.operations.get(1).size());
    Assert.assertEquals("End increment batch",1, w.operations.get(2).size());
  }

  @Test
  public void testIncrementInMiddle() {
    SendMockedWeaver w = new SendMockedWeaver();
    w.sendCreate(converter.fromJson("[ {\"timestamp\":1496067728063,\"action\":\"increment-attribute\",\"id\":\"testymctest\",\"key\":\"instanceCount\",\"value\":1}" +
      ", {\"timestamp\":1496070297249,\"action\":\"create-attribute\",\"id\":\"cj3a9mla9000j70p7izegdz8w\",\"key\":\"quantity\",\"value\":0}\n" +
      ", {\"timestamp\":1496067728063,\"action\":\"increment-attribute\",\"id\":\"testymctest\",\"key\":\"instanceCount\",\"value\":1}" +
      ", {\"timestamp\":1496070297249,\"action\":\"create-relation\",\"id\":\"cj3a9mla9000k70p7yg1bd2qo\",\"from\":\"cj3a9mla9000j70p7izegdz8w\",\"key\":\"to\",\"to\":\"cj300z2dh001069rzmrbv9nwx\"}\n" +
      ", {\"timestamp\":1496070297249,\"action\":\"create-relation\",\"id\":\"cj3a9mlaa000l70p794jiwout\",\"from\":\"cj300z3yc001269rzfzjpydpv\",\"key\":\"consistsOf\",\"to\":\"cj3a9mla9000j70p7izegdz8w\"}\n" +
      ", {\"timestamp\":1496070299515,\"action\":\"update-attribute\",\"id\":\"cj300z3yc001269rzfzjpydpv\",\"key\":\"orderOfChildren\",\"value\":\"[\\\"cj307cf2s0006ajrzonhwh5us\\\",\\\"cj300z2dh001069rzmrbv9nwx\\\",\\\"cj3016kej001m69rz4t6edgqm\\\",\\\"cj3016o34001r69rzycwiwn73\\\"]\"}]",JsonArray.class), false);
    Assert.assertEquals("Expected number of batches", 4, w.operations.size());
    Assert.assertEquals("Start increment batch",1, w.operations.get(0).size());
    Assert.assertEquals("Single ops batch",1, w.operations.get(1).size());
    Assert.assertEquals("Middle increment batch",1, w.operations.get(2).size());
    Assert.assertEquals("Other ops batch",3, w.operations.get(3).size());
  }
}
