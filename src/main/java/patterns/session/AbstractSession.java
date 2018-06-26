
package patterns.session;

import java.util.UUID;

/**
 * Abstract Session class.
 */
public abstract class AbstractSession {

    /** The uuid. */
    protected final UUID uuid = UUID.randomUUID();

    /** The token. */
    protected String token;

    /**
     * Default Constructor.
     */
    public AbstractSession() {
        this("");
    }

    /**
     * Constructor with token.
     *
     * @param token the token
     */
    public AbstractSession(final String token) {
        super();
        setToken(token);
    }

    /**
     * Sets the token.
     *
     * @param token
     *            the token to set
     */
    public void setToken(final String token) {
        this.token = token;
    }

    /**
     * Gets the uuid.
     *
     * @return the uuid
     */
    public UUID getUuid() {
        return this.uuid;
    }

    /**
     * Gets the token.
     *
     * @return the token
     */
    public String token() {
        return this.token;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("AbstractSession [uuid=%s, token=%s]", this.uuid, this.token);
    }

}
