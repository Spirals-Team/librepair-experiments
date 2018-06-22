package io.scalecube.gateway.core;

import static io.scalecube.gateway.core.TestInputs.D_STING;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GatewayMessageCodecTest {

  private final GatewayMessageCodec codec = new GatewayMessageCodec();

  @Test
  public void testEncodeNoData() throws IOException {
    ByteBuf input = toByteBuf(TestInputs.NO_DATA);

    GatewayMessage result = codec.decode(input);

    Assert.assertEquals(TestInputs.Q, result.qualifier());
    Assert.assertEquals(TestInputs.SID, result.streamId());
    Assert.assertEquals(TestInputs.SIG, result.signal());
    Assert.assertEquals(TestInputs.I, result.inactivity());
  }

  @Test
  public void testEncodeStringData() throws IOException {

    char[] arr = new char[3];
    Arrays.fill(arr, 'c');
    String qual = new String(arr);
    String stringData =
        String.format(TestInputs.STRING_DATA_PATTERN_Q_SIG_SID_D, qual, TestInputs.SIG, TestInputs.SID, "123");

    ByteBuf input = toByteBuf(stringData);
    System.out.println("Parsing JSON:" + stringData);

    GatewayMessage result = codec.decode(input);

    Assert.assertEquals(qual, result.qualifier());
    Assert.assertEquals(TestInputs.SID, result.streamId());
    Assert.assertTrue(result.data() instanceof ByteBuf);
    Assert.assertEquals("123", ((ByteBuf) result.data()).toString(StandardCharsets.UTF_8));
  }

  private ByteBuf toByteBuf(String noData) {
    ByteBuf bb = ByteBufAllocator.DEFAULT.buffer();
    bb.writeBytes(noData.getBytes());
    return bb;
  }

  @Test
  public void decode() {}
}
