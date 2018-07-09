
package patterns.session;

import java.util.UUID;

/**
 * Abstract Session class.
 */
public abstract class AbstractSession {

    /** A Universally Unique ID. */
    protected UUID uuid = UUID.randomUUID();

    /** The token. */
    protected String token;

    /**
     * Default Constructor.
     */
    public AbstractSession() {
        this(UUID.randomUUID());
    }

    public AbstractSession(final UUID currentUUID) {
        this.uuid = currentUUID;
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
        return String.format("%s [uuid=%s, token=%s]", this.getClass().getSimpleName(), this.uuid, this.token);
    }

}
