/*
 * Copyright (C) 2014 Language In Interaction
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
package nl.mpi.tg.eg.experiment.client.view;

import nl.mpi.tg.eg.experiment.client.view.ComplexView;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import nl.mpi.tg.eg.experiment.client.view.SimpleView;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import nl.mpi.tg.eg.experiment.client.model.colour.ColourData;
import nl.mpi.tg.eg.experiment.client.model.colour.StimulusResponseGroup;
import nl.mpi.tg.eg.experiment.client.model.colour.GroupScoreData;
import nl.mpi.tg.eg.experiment.client.model.colour.ScoreData;

/**
 * @since Oct 14, 2014 10:57:45 AM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class ReportView extends ComplexView {

    public void showResults(StimulusResponseGroup stimuliGroup, GroupScoreData calculatedScores) {
        int columnCount = calculatedScores.getScoreDataList().get(0).getColourData().size();
        int row = 0;
        final FlexTable grid = new FlexTable();
        grid.setStylePrimaryName("resultsTablePanel");
        final Label titleLabel = new Label(stimuliGroup.getGroupLabel());
        titleLabel.setStylePrimaryName("resultsTableTitle");
        grid.setWidget(0, 0, titleLabel);
        grid.getFlexCellFormatter().setColSpan(0, 0, columnCount + 1);
        row++;
        for (ScoreData scoreData : calculatedScores.getScoreDataList()) {
            for (int column = 0; column < columnCount; column++) {
                final Label label = new Label(scoreData.getStimulus().getLabel());
                final ColourData colour = scoreData.getColourData().get(column);
                if (colour == null) {
                    label.getElement().setAttribute("style", "color: grey;background: none;");
                } else {
                    String foreground = (colour.getRed() + colour.getGreen() + colour.getBlue() > 128 * 3) ? "#A9A9A9" : "#D3D3D3";
                    label.getElement().setAttribute("style", "background:" + foreground + ";color:rgb(" + colour.getRed() + "," + colour.getGreen() + "," + colour.getBlue() + "); padding:5px;");
                }
                grid.setWidget(row, column, label);
            }
            if (scoreData.getDistance() != null) {
                final HorizontalPanel bargraphOuter = new HorizontalPanel();
                final HorizontalPanel bargraphInner = new HorizontalPanel();
                bargraphOuter.setPixelSize(100, 10);
                bargraphInner.setPixelSize((int) (100.0 / 6 * scoreData.getDistance()), 10);
                bargraphOuter.setStyleName("bargraphOuter");
                bargraphInner.setStyleName("bargraphInner");
                bargraphOuter.add(bargraphInner);
                grid.setWidget(row, columnCount, bargraphOuter);
            }
            row++;
        }
        outerPanel.add(grid);
    }
}
