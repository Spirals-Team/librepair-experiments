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

/**
 * Node Class.
 *
 * @param <L> the generic type
 * @param <R> the generic type
 */
public abstract class AbstractNode<L, R> {

    /** The left. */
    private L left = null;

    /** The right. */
    private R right = null;

    /**
     * Instantiates a new abstract node.
     */
    public AbstractNode() {
        super();
    }

    /**
     * Instantiates a new abstract node.
     *
     * @param left the left
     * @param right the right
     */
    public AbstractNode(final L left, final R right) {
        super();
        this.left = left;
        this.right = right;
    }

    /**
     * Left.
     *
     * @return the l
     */
    protected L left() {
        return this.left;
    }

    /**
     * Right.
     *
     * @return the r
     */
    protected R right() {
        return this.right;
    }

}
