/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import nl.mpi.tg.eg.experiment.client.exception.MetadataFieldException;
import nl.mpi.tg.eg.experiment.client.model.MetadataField;
import nl.mpi.tg.eg.experiment.client.model.StimulusFreeText;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 * @since Aug 2, 2017 3:41:23 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class MetadataFieldWidget implements StimulusFreeText {

    final private MetadataField metadataField;
    final private Stimulus stimulus;
    final private String initialValue;
    final private FocusWidget focusWidget;
    final private Widget widget;
    final private Label label;
    final private Label errorLabel;
    final private VerticalPanel labelPanel;
    final private int dataChannel;
    final DateOfBirthField dateOfBirthField;

    public MetadataFieldWidget(MetadataField metadataField, Stimulus stimulus, String initialValue, final int dataChannel) {
        this.metadataField = metadataField;
        this.stimulus = stimulus;
        this.initialValue = initialValue;
        this.dataChannel = dataChannel;
        if (metadataField.isDate()) {
            label = new Label(metadataField.getFieldLabel());
//            final DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
//            final DateBox dateBox = new DateBox();
//            dateBox.getDatePicker().setYearAndMonthDropdownVisible(true);
//            dateBox.setFormat(new DateBox.DefaultFormat(dateFormat)); 
            dateOfBirthField = new DateOfBirthField();
            focusWidget = dateOfBirthField.getTextBox();
            if (initialValue != null) {
                dateOfBirthField.setDate(initialValue);
            }
            widget = dateOfBirthField;
        } else if (metadataField.isCheckBox()) {
            dateOfBirthField = null;
            label = new Label();
            focusWidget = new CheckBox(metadataField.getFieldLabel());
            ((CheckBox) focusWidget).setValue((initialValue == null) ? false : Boolean.parseBoolean(initialValue));
            widget = focusWidget;
        } else if (metadataField.isListBox()) {
            dateOfBirthField = null;
            label = new Label(metadataField.getFieldLabel());
            focusWidget = new ListBox();
            int selectedIndex = 0;
            int itemCounter = 0;
            ((ListBox) focusWidget).addItem(""); // make sure there is an empty item at the top of the list
            for (String listItem : metadataField.getListValues()) {
                if (!listItem.isEmpty()) { // allow only one empty item in this list
                    ((ListBox) focusWidget).addItem(listItem);
                    itemCounter++;
                }
                if (initialValue != null && initialValue.equals(listItem)) {
                    selectedIndex = itemCounter;
                }
            }
            ((ListBox) focusWidget).setSelectedIndex(selectedIndex);
            widget = focusWidget;
        } else {
            dateOfBirthField = null;
            label = new Label(metadataField.getFieldLabel());
            if (metadataField.isMultiLine()) {
                focusWidget = new TextArea();
            } else {
                focusWidget = new TextBox();
            }
            ((TextBoxBase) focusWidget).setText((initialValue == null) ? "" : initialValue);
            widget = focusWidget;
        }
        widget.setStylePrimaryName("metadataOK");
        errorLabel = new Label();
        labelPanel = new VerticalPanel();
        labelPanel.add(label);
        labelPanel.add(errorLabel);
    }

    @Override
    public Stimulus getStimulus() {
        return stimulus;
    }

    @Override
    public FocusWidget getFocusWidget() {
        return focusWidget;
    }

    public Widget getWidget() {
        return widget;
    }

    public IsWidget getLabel() {
        return labelPanel;
    }

    public void setValue(String fieldValue) {
        if (focusWidget instanceof CheckBox) {
            ((CheckBox) focusWidget).setValue(Boolean.valueOf(fieldValue));
        } else if (focusWidget instanceof ListBox) {
            for (int itemIndex = 0; itemIndex < ((ListBox) focusWidget).getItemCount(); itemIndex++) {
                if (((ListBox) focusWidget).getValue(itemIndex).equals(fieldValue)) {
                    ((ListBox) focusWidget).setSelectedIndex(itemIndex);
                }
            }
        } else if (focusWidget instanceof TextBoxBase) {
            ((TextBoxBase) focusWidget).setValue(fieldValue);
        }
    }

    @Override
    public String getValue() {
        if (dateOfBirthField != null) {
            return dateOfBirthField.getValue();
        } else if (focusWidget instanceof CheckBox) {
            return Boolean.toString(((CheckBox) focusWidget).getValue());
        } else if (focusWidget instanceof ListBox) {
            return ((ListBox) focusWidget).getSelectedValue();
        } else if (focusWidget instanceof TextBoxBase) {
            return ((TextBoxBase) focusWidget).getValue();
        } else {
            throw new UnsupportedOperationException("Unexpected type for: " + focusWidget.getClass());
        }
    }

    @Override
    public String getResponseTimes() {
        return null;
    }

    @Override
    public int getDataChannel() {
        return dataChannel;
    }

    @Override
    public boolean isValid() {
        try {
            metadataField.validateValue(getValue());
            focusWidget.setStylePrimaryName("metadataOK");
            label.setStylePrimaryName("metadataOK");
            errorLabel.setStylePrimaryName("metadataOK");
            errorLabel.setText("");
            return true;
        } catch (MetadataFieldException exception) {
            focusWidget.setStylePrimaryName("metadataError");
            label.setStylePrimaryName("metadataError");
            errorLabel.setStylePrimaryName("metadataErrorMessage");
            errorLabel.setText(metadataField.getControlledMessage());
            return false;
        }
    }

    @Override
    public String getPostName() {
        return metadataField.getPostName();
    }
}
