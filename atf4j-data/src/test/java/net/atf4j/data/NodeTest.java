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

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * The NodeTest Class.
 */
public final class NodeTest extends TestResultsReporting {

    /**
     * The Node Class.
     */
    public class Node extends AbstractNode<Object, Object> {

        /**
         * Instantiates a new node.
         */
        public Node() {
            super();
        }

        /**
         * Instantiates a new node.
         *
         * @param left the left
         * @param right the right
         */
        protected Node(final Object left, final Object right) {
            super(left, right);
        }

    }

    /**
     * Test node.
     */
    @Test
    public void testNode() {
        final Node node = new Node();
        assertNotNull(node);
    }

    /**
     * Test left.
     */
    @Test
    public void testLeft() {
        final Node node = new Node(new Object(), new Object());
        assertNotNull(node);
        assertNotNull(node.left());
    }

    /**
     * Test right.
     */
    @Test
    public void testRight() {
        final Node node = new Node(new Object(), new Object());
        assertNotNull(node);
        assertNotNull(node.right());
    }

}
