package de.adorsys.smartanalytics.exception.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import static de.adorsys.smartanalytics.exception.domain.Message.Severity.ERROR;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Messages implements Serializable {

    private static final long serialVersionUID = -1L;

    private String uuid;

    @Singular
    private Collection<Message> messages;

    public static Messages createError(String key) {
        return builder()
                .message(new Message(key, ERROR))
                .build();
    }

    public static Messages createError(String key, String renderedMessage) {
        return builder()
                .message(new Message(key, ERROR, renderedMessage))
                .build();
    }

    public static Messages createError(String key, String renderedMessage, Map<String, String> params) {
        return builder()
            .message(new Message(key, ERROR, renderedMessage, params))
            .build();
    }
}
