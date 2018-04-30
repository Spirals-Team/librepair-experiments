/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experiment.client.presenter.kin;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import nl.mpi.kinnate.kindata.KinRectangle;
import nl.mpi.kinnate.svg.KinElement;
import nl.mpi.kinnate.svg.KinElementException;
import nl.mpi.kinnate.svg.MouseListenerSvg;

/**
 * @since Jul 1, 2016 18:18:15 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class KinElementGwt implements KinElement {

    final Node node;

    public KinElementGwt(Node node) {
        this.node = node;
    }

    @Override
    public void setAttribute(String name, String value) throws KinElementException {
        ((Element) node).setAttribute(name, value);
    }

    @Override
    public KinElement appendChild(KinElement newChild) throws KinElementException {
        final Node appendedChild = node.appendChild(((KinElementGwt) newChild).node);
        return (appendedChild != null) ? new KinElementGwt(appendedChild) : null;
    }

    @Override
    public KinElement removeChild(KinElement oldChild) throws KinElementException {
        final Node removeChild = node.removeChild(((KinElementGwt) oldChild).node);
        return (removeChild != null) ? new KinElementGwt(removeChild) : null;
    }

    @Override
    public KinElement insertBefore(KinElement newChild, KinElement refChild) throws KinElementException {
        final Node insertBefore = node.insertBefore(((KinElementGwt) newChild).node, ((KinElementGwt) refChild).node);
        return (insertBefore != null) ? new KinElementGwt(insertBefore) : null;
    }

    @Override
    public KinElement getFirstChild() {
        final Node getFirstChild = node.getFirstChild();
        return (getFirstChild != null) ? new KinElementGwt(getFirstChild) : null;
    }

    @Override
    public KinElement getNextSibling() {
        final Node getNextSibling = node.getNextSibling();
        return (getNextSibling != null) ? new KinElementGwt(getNextSibling) : null;
    }

    @Override
    public KinElement getParentNode() {
        final Node getParentNode = node.getParentNode();
        return (getParentNode != null) ? new KinElementGwt(getParentNode) : null;
    }

    @Override
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws KinElementException {
        setAttributeNS(node, namespaceURI, qualifiedName, value);
    }

    @Override
    public String getAttributeNS(String namespaceURI, String localName) throws KinElementException {
        throw new UnsupportedOperationException("Not supported yet.8"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAttribute(String attributeName) throws KinElementException {
        return ((Element) node).getAttribute(attributeName);
    }

    @Override
    public String getLocalName() {
        return ((Element) node).getTagName();
    }

    @Override
    public String getTagName() throws KinElementException {
        throw new UnsupportedOperationException("Not supported yet.9"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTextContent(String textContent) throws KinElementException {
        throw new UnsupportedOperationException("Not supported yet.10"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTextContent() throws KinElementException {
        throw new UnsupportedOperationException("Not supported yet.11"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeAttribute(String name) throws KinElementException {
        ((Element) node).removeAttribute(name);
    }

    @Override
    public boolean isElement() {
        return node != null;
    }

    @Override
    public void addEventListener(MouseListenerSvg mouseListenerSvg) {
        addEventListener(node, (MouseListenerGwt) mouseListenerSvg);
    }

    public KinRectangle getBBox() {
        return getBBox(node);
    }

    public KinRectangle getBoundingClientRect() {
        return getBoundingClientRect(node);
    }

    public static native KinRectangle getBoundingClientRect(Node node)/*-{ 
        var boundingClientRect = node.getBoundingClientRect();
            return boundingClientRect;
    }-*/;

    public static native KinRectangle getBBox(Node node)/*-{ 
        var getBBox = node.getBBox();
        return @nl.mpi.kinnate.kindata.KinRectangle::new(IIII)(getBBox.x, getBBox.y, getBBox.width, getBBox.height);
    }-*/;

    public static native Element createTextNode(String data)/*-{ 
        return $doc.createTextNode(data);
    }-*/;

    public static native Element createElementNS(String namespaceURI, String qualifiedName)/*-{ 
        return $doc.createElementNS(namespaceURI, qualifiedName);
    }-*/;

    public static native void setAttributeNS(Node node, String namespaceURI, String qualifiedName, String value)/*-{ 
        node.setAttributeNS(namespaceURI, qualifiedName, value);
    }-*/;

    public static native void addEventListener(Node node, MouseListenerGwt mouseListenerSvg)/*-{ 
        node.onclick=function(event){
            var isLeftMouseButton = event.which == 1;
            var shiftDown = event.shiftKey;
            var kinPoint = @nl.mpi.kinnate.kindata.KinPoint::new(II)(event.clientX, event.clientY);
            mouseListenerSvg.@nl.mpi.tg.eg.experiment.client.presenter.kin.MouseListenerGwt::mouseReleased(Lnl/mpi/kinnate/kindata/KinPoint;Ljava/lang/Boolean;Ljava/lang/Boolean;)(kinPoint, isLeftMouseButton, shiftDown);
//            mouseListenerSvg.@nl.mpi.tg.eg.experiment.client.presenter.kin.MouseListenerGwt::mouseReleased()();
        };
    }-*/;
}
