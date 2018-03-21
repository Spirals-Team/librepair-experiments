/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics, Nijmegen
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

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import java.util.Random;
import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;

/**
 * @since Aug 6, 2015 5:17:24 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class KinTypeView extends TimedStimulusView {

    private final TextBox labelBox1 = new TextBox();
    private final TextBox labelBox2 = new TextBox();
    private final TextBox yearOfBirthBox = new TextBox();
    private final TextBox yearOfDeathBox = new TextBox();
    private final TextBox monthOfBirthBox = new TextBox();
    private final TextBox dayOfBirthBox = new TextBox();
    private ListBox relationType;

    public KinTypeView(AudioPlayer audioPlayer) {
        super(audioPlayer);
    }

    public void addKinTypeGui() {
        FlexTable flexTable = new FlexTable();

        relationType = new ListBox();
        relationType.addItem("Ego Ungendered", "|E");
        relationType.addItem("Ego Male", "|Em");
        relationType.addItem("Ego Female", "|Ef");
        relationType.addItem("Mother", "M");
        relationType.addItem("Father", "F");
        relationType.addItem("Son", "S");
        relationType.addItem("Daughter", "D");
        relationType.addItem("Wife", "W");
        relationType.addItem("Husband", "H");
//        relationType.addItem("Spouse", "Sp");

        relationType.setSelectedIndex(new Random().nextInt(relationType.getItemCount() - 3) + 3);
        flexTable.setWidget(0, 0, new Label("Relation Type"));
        flexTable.setWidget(0, 1, relationType);
        flexTable.setWidget(1, 0, new Label("Label 1"));
        flexTable.setWidget(1, 1, labelBox1);
        flexTable.setWidget(2, 0, new Label("Label 2"));
        flexTable.setWidget(2, 1, labelBox2);
        flexTable.setWidget(3, 0, new Label("Birth YYYY/MM/DD"));
        flexTable.setWidget(3, 1, yearOfBirthBox);
//        flexTable.setWidget(3, 2, new Label("Month Of Birth"));
//        flexTable.setWidget(3, 3, monthOfBirthBox);
//        flexTable.setWidget(3, 4, new Label("Day Of Birth"));
//        flexTable.setWidget(3, 5, dayOfBirthBox);
        flexTable.setWidget(4, 0, new Label("Death YYYY/MM/DD"));
        flexTable.setWidget(4, 1, yearOfDeathBox);
        outerPanel.add(flexTable);
    }

    public String getKinTypeString() {// + "/" + monthOfBirthBox.getValue() + "/" + dayOfBirthBox.getValue()
        String kts = "";
        String datePortion = yearOfBirthBox.getValue() + "-" + yearOfDeathBox.getValue();
        final String labelPortion;
        if (!labelBox1.getText().isEmpty() && !labelBox2.getValue().isEmpty()) {
            labelPortion = labelBox1.getText() + ";" + labelBox2.getValue();
        } else {
            labelPortion = labelBox1.getText() + labelBox2.getValue();
        }
        if (!labelPortion.isEmpty()) {
            kts = kts + ":" + labelPortion;
        }
        if (datePortion.length() > 1) {
            kts = (kts.isEmpty()) ? kts + ":;" + datePortion : kts + ";" + datePortion;
        }
        if (!kts.isEmpty()) {
            kts = kts + ":";
        }
        return relationType.getSelectedValue() + kts;
    }
}
