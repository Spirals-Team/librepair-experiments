/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.data;

import net.atf4j.core.TestResultsReporting;

/**
 * Datum for providing field status.
 */
public abstract class AbstractDatum extends TestResultsReporting {

    /** The is fresh. */
    private boolean isFresh = true;

    /** The is changed. */
    private boolean isChanged = false;

    /** The is deleted. */
    private boolean isDeleted = false;

    /** The is used. */
    private boolean isUsed = false;

    /**
     * Fresh.
     *
     * @param isNew the is new
     * @return the datum
     */
    protected AbstractDatum fresh(final boolean isNew) {
        this.isFresh = isNew;
        this.isChanged = !this.isFresh;
        return this;
    }

    /**
     * Changed.
     *
     * @param isDirty the is dirty
     * @return the datum
     */
    protected AbstractDatum changed(final boolean isDirty) {
        this.isChanged = isDirty;
        this.isFresh = !this.isChanged;
        return this;
    }

    /**
     * Deleted.
     *
     * @param isDeleted the is deleted
     * @return the datum
     */
    protected AbstractDatum deleted(final boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    /**
     * Used.
     *
     * @param isUsed the is used
     * @return the datum
     */
    protected AbstractDatum used(final boolean isUsed) {
        this.isUsed = isUsed;
        return this;
    }

    /**
     * Checks if is new.
     *
     * @return true, if is new
     */
    public boolean isFresh() {
        return this.isFresh;
    }

    /**
     * Checks if is dirty.
     *
     * @return true, if is dirty
     */
    public boolean isChanged() {
        return this.isChanged;
    }

    /**
     * Checks if is deleted.
     *
     * @return true, if is deleted
     */
    public boolean isDeleted() {
        return this.isDeleted;
    }

    /**
     * Checks if is used.
     *
     * @return true, if is used
     */
    public boolean isUsed() {
        return this.isUsed;
    }

    /**
     * Debug string.
     *
     * @return the string
     */
    public String debugString() {
        return String.format("%s [isNew=%s, isDirty=%s, isDeleted=%s, isUsed=%s]",
                this.getClass().getSimpleName(),
                this.isFresh,
                this.isChanged,
                this.isDeleted,
                this.isUsed);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return debugString();
    }

}
