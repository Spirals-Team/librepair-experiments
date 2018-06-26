
package coaching.pool;

/**
 * Abstract Value Object.
 */
public abstract class AbstractValueObject {

    /** Indicates if the value dirty, has it been changed. */
    private boolean dirty = false;

    /**
     * Mark as dirty, changed.
     */
    protected void markDirty() {
        this.dirty = true;
    }

    /**
     * Checks if the value is dirty.
     *
     * @return true, if is dirty
     */
    protected boolean isDirty() {
        return this.dirty;
    }

}
