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

import java.util.ArrayList;
import nl.mpi.kinnate.kindata.DataTypes;
import nl.mpi.kinnate.kindata.KinPoint;
import nl.mpi.kinnate.svg.KinElement;
import nl.mpi.kinnate.svg.KinElementException;
import nl.mpi.kinnate.svg.MouseListenerSvg;
import nl.mpi.kinnate.svg.OldFormatException;
import nl.mpi.kinnate.svg.RelationDragHandle;
import nl.mpi.kinnate.svg.SvgUpdateHandler;
import nl.mpi.kinnate.uniqueidentifiers.IdentifierException;
import nl.mpi.kinnate.uniqueidentifiers.UniqueIdentifier;

/**
 * @since Jul 1, 2016 18:28:27 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class MouseListenerGwt implements MouseListenerSvg {

    private final KinElement targetNode;
    private final SvgUpdateHandler svgUpdateHandler;

    public MouseListenerGwt(KinElement targetNode, SvgUpdateHandler svgUpdateHandler) {
        this.targetNode = targetNode;
        this.svgUpdateHandler = svgUpdateHandler;
    }

    @Override
    public void mousePressed(Boolean isPopupTrigger) {
        throw new UnsupportedOperationException("Not supported yet.12"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(KinPoint kinPoint, Boolean isMiddleMouseButton, Boolean isLeftMouseButton, Boolean shiftDown) {
        throw new UnsupportedOperationException("Not supported yet.13"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(KinPoint kinPoint, Boolean isLeftMouseButton, Boolean shiftDown) {
        try {
            final ArrayList identifierList = new ArrayList<>();
            final String attribute = targetNode.getAttribute("id");
            final UniqueIdentifier uniqueIdentifier = new UniqueIdentifier(attribute);
            identifierList.add(uniqueIdentifier);
            svgUpdateHandler.updateSvgSelectionHighlightsI(identifierList, this);
            if (!uniqueIdentifier.isTransientIdentifier()) {
                svgUpdateHandler.setRelationDragHandle(new RelationDragHandle(null, DataTypes.RelationType.ancestor, 310, 00, 120, 60, 1));
                svgUpdateHandler.updateMouseDrag(identifierList, 310, 10);
                svgUpdateHandler.showAddEntityBox(310, 0);
            }
        } catch (KinElementException | IdentifierException | OldFormatException elementException) {
            throw new UnsupportedOperationException("Not supported yet: " + elementException.getMessage());
        }
    }
}
