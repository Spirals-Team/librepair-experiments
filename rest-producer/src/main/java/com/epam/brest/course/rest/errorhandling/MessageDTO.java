package com.epam.brest.course.rest.errorhandling;

/**
 * my message class this class take message type and message.
 */
public class MessageDTO {
    /**
     * message.
     */
    private String message;
    /**
     *type.
     */
    private MessageType type;

    /**
     *constructor.
     */
    public MessageDTO() {
    }

    /**
     *
     * @param type1 enum type.
     * @param message1 and message.
     */
    public MessageDTO(final MessageType type1, final String message1) {
        this.message = message1;
        this.type = type1;
    }

    /**
     *
     * @return messgage
     */
    public final String getMessage() {
        return message;
    }

    /**
     *
     * @param message1 setter.
     */
    public final void setMessage(final String message1) {
        this.message = message1;
    }

    /**
     *
     * @return return type.
     */
    public final MessageType getType() {
        return type;
    }

    /**
     *
     * @param type1 for setter.
     */
    public final void setType(final MessageType type1) {
        this.type = type1;
    }
}
