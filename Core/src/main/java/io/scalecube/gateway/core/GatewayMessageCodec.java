package io.scalecube.gateway.core;

import static io.scalecube.gateway.core.GatewayMessage.DATA_FIELD;
import static io.scalecube.gateway.core.GatewayMessage.INACTIVITY_FIELD;
import static io.scalecube.gateway.core.GatewayMessage.QUALIFIER_FIELD;
import static io.scalecube.gateway.core.GatewayMessage.SIGNAL_FIELD;
import static io.scalecube.gateway.core.GatewayMessage.STREAM_ID_FIELD;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Encodes/decodes {@link GatewayMessage} to/from {@link ByteBuf}.
 */
public class GatewayMessageCodec {
  private static final Logger LOGGER = LoggerFactory.getLogger(GatewayMessageCodec.class);

  private final JsonFactory jsonFactory = new JsonFactory();

  /**
   * Encode given {@code message} to given {@code byteBuf}
   *
   * @param message - input message to be encoded.
   * @param byteBuf - output byteBuffer to be written to.
   * @throws Exception in case of issues during encoding.
   */
  public void encode(GatewayMessage message, ByteBuf byteBuf) throws Exception {

    JsonGenerator generator =
        jsonFactory.createGenerator((OutputStream) new ByteBufOutputStream(byteBuf), JsonEncoding.UTF8);
    generator.writeStartObject();

    if (message.qualifier() != null) {
      generator.writeStringField(QUALIFIER_FIELD, message.qualifier());
    }
    if (message.streamId() != null) {
      generator.writeNumberField(STREAM_ID_FIELD, message.streamId());
    }
    if (message.signal() != null) {
      generator.writeNumberField(SIGNAL_FIELD, message.signal());
    }

    if (message.inactivity() != null) {
      generator.writeNumberField(INACTIVITY_FIELD, message.inactivity());
    }

    // data
    Object data = message.data();
    if (data != null) {
      if (data instanceof byte[]) {
        generator.writeFieldName(DATA_FIELD);
        generator.writeRawValue(new String((byte[]) data, Charset.forName("UTF-8")));
      } else if (data instanceof String) {
        generator.writeFieldName(DATA_FIELD);
        generator.writeRawValue((String) data);
      } else if (data instanceof ByteBuf) {
        // Avoid copying, write from data's ByteBuf "as is"
        ByteBuf dataBin = (ByteBuf) data;
        if (dataBin.readableBytes() > 0) {
          generator.writeFieldName(DATA_FIELD);
          generator.writeRaw(":");
          generator.flush();
          byteBuf.writeBytes(dataBin);
        }
      }
    }

    generator.writeEndObject();
    generator.close();
  }


  /**
   * Decodes {@link GatewayMessage} from given {@code byteBuf}.
   *
   * @param byteBuf - contains raw {@link GatewayMessage} to be decoded.
   *
   * @return Decoded {@link GatewayMessage}.
   * @throws IOException - in case of issues during deserialization.
   */
  public GatewayMessage decode(ByteBuf byteBuf) throws IOException {
    ByteBuf slice = byteBuf.slice();
    try (InputStream stream = new ByteBufInputStream(slice)) {
      JsonParser jp = jsonFactory.createParser(stream);
      GatewayMessage.Builder builder = GatewayMessage.builder();

      JsonToken current = jp.nextToken();
      if (current != JsonToken.START_OBJECT) {
        LOGGER.error("Root should be object: {}", byteBuf.toString(Charset.defaultCharset()));
        throw new IOException("Failed to decode message");
      }
      long dataStart = 0;
      long dataEnd = -1;
      while (jp.nextToken() != JsonToken.END_OBJECT) {
        String fieldName = jp.getCurrentName();
        current = jp.nextToken();
        dataStart = jp.getCurrentLocation().getByteOffset();
        switch (fieldName) {
          case QUALIFIER_FIELD:
            builder.qualifier(jp.getValueAsString());
            break;
          case STREAM_ID_FIELD:
            builder.streamId(jp.getValueAsInt());
            break;
          case SIGNAL_FIELD:
            builder.signal(jp.getValueAsInt());
            break;
          case INACTIVITY_FIELD:
            builder.inactivity(jp.getValueAsInt());
            break;
          case DATA_FIELD:
            if (current.isStructStart()) {
              jp.skipChildren();
            } else {
              jp.getValueAsInt();
            }
            dataEnd = jp.getCurrentLocation().getByteOffset();
          default:
            break;
        }
      }
      System.out.println("Data:" + dataStart + "-" + dataEnd);
      if (dataEnd > 0) {
        // let's hope it's not really long
        builder.data(byteBuf.slice((int) dataStart, (int) (dataEnd - dataStart)));
      }
      return builder.build();
    } catch (Throwable ex) {
      LOGGER.error("Failed to decode message: {}", byteBuf.toString(Charset.defaultCharset()), ex);
      throw new IOException("Failed to decode message");
    }
  }
}
