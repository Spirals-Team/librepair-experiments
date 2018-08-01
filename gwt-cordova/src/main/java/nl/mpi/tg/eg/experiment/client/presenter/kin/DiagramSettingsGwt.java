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

import nl.mpi.kinnate.kindata.DataTypes;
import nl.mpi.kinnate.kindata.RelationTypeDefinition;
import nl.mpi.kinnate.svg.DiagramSettings;
import nl.mpi.kinnate.svg.KinDocument;

/**
 * @since Jul 1, 2016 18:28:35 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class DiagramSettingsGwt implements DiagramSettings {

    @Override
    public String defaultSymbol() {
        return "square";
    }

    @Override
    public boolean showIdLabels() {
        return true;
    }

    @Override
    public boolean showLabels() {
        return true;
    }

    @Override
    public boolean showKinTypeLabels() {
        return true;
    }

    @Override
    public boolean showDateLabels() {
        return true;
    }

    @Override
    public boolean showExternalLinks() {
        return true;
    }

    @Override
    public boolean highlightRelationLines() {
        return true;
    }

    @Override
    public boolean snapToGrid() {
        return true;
    }

    @Override
    public boolean showDiagramBorder() {
        return true;
    }

    @Override
    public boolean showSanguineLines() {
        return true;
    }

    @Override
    public boolean showKinTermLines() {
        return true;
    }

    @Override
    public RelationTypeDefinition[] getRelationTypeDefinitions() {
        return new DataTypes().getReferenceRelations();
    }

    @Override
    public void storeAllData(KinDocument kinDocument) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
