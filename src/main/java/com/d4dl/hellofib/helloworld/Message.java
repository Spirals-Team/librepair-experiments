package com.d4dl.hellofib.helloworld;
import org.springframework.hateoas.ResourceSupport;

/**
 * HATEOS bean encapsulating a simple message world messages extending {@link ResourceSupport} to
 * facilitate construction HATEOS entities.
 */
public class Message extends ResourceSupport {
    private String content;

    /**
     * Default constructor for serialization
     */
    public Message() {
    }

    /**
     * Single parameter constructor wrapping the message content
     * @param content the wrapped value of the message to be displayed
     */
    public Message(String content) {
        this.content = content;
    }

    /**
     * Retrieve the content of this message.
     * @return the content that should be used to display what this message says.
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the content for this message
     * @param content the content that should be used to display what this message says.
     */
    public void setContent(String content) {
        this.content = content;
    }
}
