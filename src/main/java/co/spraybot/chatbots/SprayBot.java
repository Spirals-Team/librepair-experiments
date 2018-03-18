package co.spraybot.chatbots;

import co.spraybot.ChatBot;
import co.spraybot.ChatMessage;
import co.spraybot.ChatMessageBuilder;
import co.spraybot.messagecodecs.ChatMessageCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SprayBot extends AbstractVerticle implements ChatBot {

    private String name;
    private Queue<Map<String, Object>> patternCommands = new LinkedList<>();
    private EventBus eventBus;

    public SprayBot(EventBus eventBus, String name) {
        this.eventBus = eventBus;
        this.name = name;
    }

    public SprayBot(EventBus eventBus) {
        this(eventBus, "spraybot");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void programCommand(String messagePattern, String commandName) {
        patternCommands.add(this.routeInfo(messagePattern, commandName));
    }

    @Override
    public void programCommandMentionOnly(String messagePattern, String commandName) {
        patternCommands.add(routeInfo("@?" + getName() + " " + messagePattern, commandName));
    }

    private Map<String, Object> routeInfo(String pattern, String commandName) {
        Map<String, Object> routeInfo = new HashMap<>();
        LinkedList<String> attrNames = new LinkedList<>();
        String parsedPattern = this.parsePatternForAttributes(pattern, attrNames);

        routeInfo.put("pattern", parsedPattern);
        routeInfo.put("commandName", commandName);
        routeInfo.put("originalPattern", pattern);
        routeInfo.put("attrNames", attrNames);

        return routeInfo;
    }

    private String parsePatternForAttributes(String pattern, LinkedList<String> attrNames) {
        String parsedPattern = pattern;
        Matcher attrMatcher = Pattern.compile("\\{([a-z]+)\\}").matcher(pattern);

        while (attrMatcher.find()) {
            String groupName = attrMatcher.group(1);
            parsedPattern = parsedPattern.replace("{" + groupName + "}", "(?<" + groupName + ">.+)");
            attrNames.add(groupName);
        }

        return parsedPattern;
    }

    @Override
    public void start(Future<Void> future) throws Exception {
        super.start();
        eventBus.consumer("spraybot.chatbot", message -> {
            ChatMessage chatMessage = (ChatMessage) message.body();
            chatMessageReceived(chatMessage);
        }).completionHandler(ar -> {
            future.complete();
        });
    }

    @Override
    public void chatMessageReceived(ChatMessage chatMessage) {
        for (Map<String, Object> routeInfo : patternCommands) {
            ChatMessageDeliveryOptionsPair pair = processChatMessage(chatMessage, routeInfo);
            if (pair != null) {
                DeliveryOptions deliveryOptions = pair.getDeliveryOptions();
                deliveryOptions.setCodecName(ChatMessageCodec.class.getSimpleName());
                eventBus.send("spraybot.chatcommandrunner", pair.getChatMessage(), deliveryOptions, msg -> {
                    Boolean succeeded = msg.succeeded();
                });
                break;
            }
        }
    }

    private ChatMessageDeliveryOptionsPair processChatMessage(ChatMessage chatMessage, Map<String, Object> routeInfo) {
        Matcher matcher = Pattern.compile((String) routeInfo.get("pattern")).matcher(chatMessage.getBody());

        if (!matcher.matches()) {
            return null;
        }

        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("op", "performCommand")
               .addHeader("commandName", (String) routeInfo.get("commandName"));
        ChatMessageBuilder builder = ChatMessage.builder().fromChatMessage(chatMessage);

        LinkedList<String> attrNames = (LinkedList<String>) routeInfo.get("attrNames");
        for (String attr : attrNames) {
            builder.addBodyAttribute(attr, matcher.group(attr));
        }

        return new ChatMessageDeliveryOptionsPair(builder.build(), options);
    }

    private class ChatMessageDeliveryOptionsPair {
        private ChatMessage chatMessage;
        private DeliveryOptions deliveryOptions;

        ChatMessageDeliveryOptionsPair(ChatMessage chatMessage, DeliveryOptions deliveryOptions) {
            this.chatMessage = chatMessage;
            this.deliveryOptions = deliveryOptions;
        }

        private ChatMessage getChatMessage() {
            return chatMessage;
        }

        private DeliveryOptions getDeliveryOptions() {
            return deliveryOptions;
        }
    }

}
