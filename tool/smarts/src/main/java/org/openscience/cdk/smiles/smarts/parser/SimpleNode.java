/* Copyright (C) 2004-2007  The Chemistry Development Kit (CDK) project
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * (or see http://www.gnu.org/copyleft/lesser.html)
 */
package org.openscience.cdk.smiles.smarts.parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Basic implementation of AST nodes. Automatically generated by JJTree with
 * some manual changes.
 *
 * @author Dazhi Jiao
 * @cdk.created 2007-04-24
 * @cdk.module smarts
 * @cdk.githash
 * @cdk.keyword SMARTS AST
 */
class SimpleNode implements Node, Cloneable {

    protected Node         parent;

    protected Node[]       children;

    protected int          id;

    protected SMARTSParser parser;

    public SimpleNode(int i) {
        id = i;
    }

    public SimpleNode(SMARTSParser p, int i) {
        this(i);
        parser = p;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() {
        Constructor constructor = null;
        Node clone = null;
        try {
            constructor = this.getClass().getConstructor(new Class[]{SMARTSParser.class, Integer.TYPE});
            clone = (Node) constructor.newInstance(new Object[]{parser, Integer.valueOf(id)});
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            ex.printStackTrace();
        }

        clone.jjtSetParent(parent);
        if (this.jjtGetNumChildren() > 0) {
            for (int i = 0; i < children.length; i++) {
                clone.jjtAddChild((Node) ((SimpleNode) children[i]).clone(), i);
            }
        }
        return clone;
    }

    @Override
    public void jjtOpen() {}

    @Override
    public void jjtClose() {}

    @Override
    public void jjtSetParent(Node n) {
        parent = n;
    }

    @Override
    public Node jjtGetParent() {
        return parent;
    }

    @Override
    public void jjtAddChild(Node n, int i) {
        if (children == null) {
            children = new Node[i + 1];
        } else if (i >= children.length) {
            Node c[] = new Node[i + 1];
            System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = n;
    }

    @Override
    public void jjtRemoveChild(int i) {
        if (i >= children.length) return;
        Node[] c = new Node[children.length - 1];
        System.arraycopy(children, 0, c, 0, i);
        if (i < c.length) {
            System.arraycopy(children, i + 1, c, i, c.length - i);
        }
        children = c;
    }

    @Override
    public Node jjtGetChild(int i) {
        return children[i];
    }

    @Override
    public int jjtGetNumChildren() {
        return (children == null) ? 0 : children.length;
    }

    /** Accept the visitor. * */
    @Override
    public Object jjtAccept(SMARTSParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    /** Accept the visitor. * */
    public Object childrenAccept(SMARTSParserVisitor visitor, Object data) {
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                children[i].jjtAccept(visitor, data);
            }
        }
        return data;
    }

    /*
     * You can override these two methods in subclasses of SimpleNode to
     * customize the way the node appears when the tree is dumped. If your
     * output uses more than one line you should override toString(String),
     * otherwise overriding toString() is probably all you need to do.
     */

    @Override
    public String toString() {
        return SMARTSParserTreeConstants.jjtNodeName[id];
    }

    public String toString(String prefix) {
        return prefix + toString();
    }

    /*
     * Override this method if you want to customize how the node dumps out its
     * children.
     */

    public void dump(String prefix) {
        System.out.println(toString(prefix));
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                SimpleNode n = (SimpleNode) children[i];
                if (n != null) {
                    n.dump(prefix + " ");
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SMARTSParser getParser() {
        return parser;
    }

    public void setParser(SMARTSParser parser) {
        this.parser = parser;
    }
}
