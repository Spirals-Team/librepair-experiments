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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HTMLPanel;
import java.io.IOException;
import nl.mpi.kinnate.kindata.KinPoint;
import nl.mpi.kinnate.kindata.KinRectangle;
import nl.mpi.kinnate.svg.KinDocument;
import nl.mpi.kinnate.svg.KinElement;
import nl.mpi.kinnate.svg.KinElementException;
import nl.mpi.kinnate.svg.SvgUpdateHandler;
import nl.mpi.tg.eg.experiment.client.model.UserId;

/**
 * @since Jul 1, 2016 18:23:11 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class KinDocumentGwt implements KinDocument {

    private HTMLPanel htmlDoc;
    private final SvgUpdateHandler svgUpdateHandler;

    public KinDocumentGwt(SvgUpdateHandler svgUpdateHandler) {
        this.svgUpdateHandler = svgUpdateHandler;
    }

    public HTMLPanel getHtmlDoc() {
        return htmlDoc;
    }

    @Override
    public void readDocument(String uri, String templateXml) throws IOException {
        htmlDoc = new HTMLPanel(templateXml);
    }

    @Override
    public void createDocument(String uri) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.1"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public KinElement getElementById(String elementId) {
        final Element elementById = Document.get().getElementById(elementId);
        return (elementById != null) ? new KinElementGwt(elementById) : null;
    }

    @Override
    public KinElement getDocumentElement() {
        return new KinElementGwt(htmlDoc.getElement().getElementsByTagName("svg").getItem(0));
    }

    @Override
    public KinElement createElementNS(String namespaceURI, String qualifiedName) throws KinElementException {
        final Element createElementNS = KinElementGwt.createElementNS(namespaceURI, qualifiedName);
        return (createElementNS != null) ? new KinElementGwt(createElementNS) : null;
    }

    @Override
    public KinElement createTextNode(String data) {
        final Element createTextNode = KinElementGwt.createTextNode(data);
        return (createTextNode != null) ? new KinElementGwt(createTextNode) : null;
    }

    @Override
    public void addEventListener(final KinElement targetNode) {
        targetNode.addEventListener(new MouseListenerGwt(targetNode, svgUpdateHandler));
    }

    @Override
    public KinPoint getPointOnDocument(KinPoint screenLocation, KinElement targetGroupElement) {
        throw new UnsupportedOperationException("Not supported yet.2"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public KinPoint getPointOnScreen(KinPoint documentLocation, KinElement targetGroupElement) {
        throw new UnsupportedOperationException("Not supported yet.3"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public KinRectangle getRectOnDocument(KinRectangle screenRectangle, KinElement targetGroupElement) {
        throw new UnsupportedOperationException("Not supported yet.4"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public KinRectangle getBoundingBox(KinElement selectedGroup) {
        return ((KinElementGwt) selectedGroup).getBBox();
    }

    @Override
    public float getDragScale(KinElement kinElement) {
        throw new UnsupportedOperationException("Not supported yet.5"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUUID() {
        return new UserId().toString();
    }
}
