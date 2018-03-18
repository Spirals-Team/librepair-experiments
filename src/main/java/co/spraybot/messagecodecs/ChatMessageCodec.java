package co.spraybot.messagecodecs;

import co.spraybot.ChatAdapter;
import co.spraybot.ChatMessage;
import co.spraybot.User;
import co.spraybot.ChatMessageBuilder;
import co.spraybot.DefaultUser;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ChatMessageCodec extends BaseCodec<ChatMessage, ChatMessage> {
    @Override
    public void encodeToWire(Buffer buffer, ChatMessage chatMessage) {
        JsonObject json = new JsonObject();

        json.put("chatAdapter", chatMessage.getAdapterName());
        json.put("room", chatMessage.getRoomName());
        json.put("user:id", chatMessage.getUser().getId());
        json.put("user:name", chatMessage.getUser().getName());
        json.put("timestamp", chatMessage.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        JsonObject jsonHeaders = new JsonObject();
        chatMessage.getHeaders().forEach(jsonHeaders::put);
        json.put("headers", jsonHeaders);

        JsonObject jsonAttributes = new JsonObject();
        chatMessage.getBodyAttributes().forEach(jsonAttributes::put);
        json.put("attributes", jsonAttributes);

        json.put("body", chatMessage.getBody());

        String bufferContents = json.encode();
        int length = bufferContents.getBytes().length;

        buffer.appendInt(length);
        buffer.appendString(bufferContents);
    }

    @Override
    public ChatMessage decodeFromWire(int position, Buffer buffer) {
        int start = position + 4;
        int length = buffer.getInt(position) + 4;

        String encodedJson = buffer.getString(start, length);
        JsonObject json = new JsonObject(encodedJson);
        ChatMessageBuilder builder = ChatMessage.builder();

        String chatAdapter = json.getString("chatAdapter");
        User user = new DefaultUser(json.getString("user:id"), json.getString("user:name"));
        String timestamp = json.getString("timestamp");
        builder.poweredBy(chatAdapter)
                .sentFromRoom(json.getString("room"), user)
                .sentAt(LocalDateTime.parse(timestamp));

        Map<String, String> headers = json.getJsonObject("headers").mapTo(Map.class);
        headers.forEach(builder::addHeader);

        Map<String, String> attributes = json.getJsonObject("attributes").mapTo(Map.class);
        attributes.forEach(builder::addBodyAttribute);

        builder.body(json.getString("body"));

        return builder.build();
    }

}
