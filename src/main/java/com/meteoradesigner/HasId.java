package com.meteoradesigner;

import java.io.Serializable;

/**
 * This interface @code{HasId} holds core semantic to spread the ID attribute.
 */
public interface HasId extends Serializable {

    /**
     * Gets the id.
     *
     * @return <code>Integer</code> instance id.
     */
    Integer getId();

    /**
     * Sets the id.
     *
     * @param idToSet the id to set.
     */
    void setId(Integer idToSet);

    /**
     * Checks if the subclass instance is new.
     *
     * @return true if the instance is new.
     */
    default boolean isNew() {
        return getId() == null;
    }
}
