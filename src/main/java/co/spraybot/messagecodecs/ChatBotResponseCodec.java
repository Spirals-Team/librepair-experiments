package co.spraybot.messagecodecs;

import co.spraybot.ChatBotResponse;
import co.spraybot.ChatBotResponseBuilder;
import co.spraybot.ChatMessage;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatBotResponseCodec extends BaseCodec<ChatBotResponse, ChatBotResponse> {

    private ChatMessageCodec chatMessageCodec = new ChatMessageCodec();

    @Override
    public void encodeToWire(Buffer buffer, ChatBotResponse chatBotResponse) {
        JsonObject bufferData = new JsonObject();
        Buffer chatMessageBuffer = new BufferImpl();
        chatMessageCodec.encodeToWire(chatMessageBuffer, chatBotResponse.getChatMessageRespondingTo());

        int start = 4;
        int end = chatMessageBuffer.getInt(0) + 4;
        String chatMessageContent = chatMessageBuffer.getString(start, end);

        bufferData.put("timestamp", chatBotResponse.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        bufferData.put("body", chatBotResponse.getBody());
        bufferData.put("isDirectMessage", chatBotResponse.isDirectMessage());
        bufferData.put("chatMessage", chatMessageContent);

        String bufferString = bufferData.encode();
        int bufferLength = bufferString.getBytes().length;

        buffer.appendInt(bufferLength);
        buffer.appendString(bufferString);
    }

    @Override
    public ChatBotResponse decodeFromWire(int position, Buffer buffer) {
        int start = position + 4;
        int length = buffer.getInt(position) + 4;
        String encodedJson = buffer.getString(start, length);
        JsonObject json = new JsonObject(encodedJson);

        ChatBotResponseBuilder builder = new ChatBotResponseBuilder();

        builder.sentAt(LocalDateTime.parse(json.getString("timestamp")))
            .body(json.getString("body"));

        if (json.getBoolean("isDirectMessage")) {
            builder.asDirectMessage();
        }

        JsonObject jsonChatMessage = new JsonObject(json.getString("chatMessage"));

        Buffer chatMessageBuffer = new BufferImpl();

        String chatMessageString = jsonChatMessage.encode();
        int chatMessageLength = chatMessageString.getBytes().length;

        chatMessageBuffer.appendInt(chatMessageLength);
        chatMessageBuffer.appendString(chatMessageString);

        ChatMessage chatMessage = chatMessageCodec.decodeFromWire(0, chatMessageBuffer);

        builder.respondingTo(chatMessage);

        return builder.build();
    }
}
