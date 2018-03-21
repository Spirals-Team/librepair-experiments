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
package nl.mpi.tg.eg.experiment.client.view;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.IntegerBox;

/**
 * @since Aug 23, 2016 5:02:34 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class DateOfBirthField extends HorizontalPanel {

    final DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
    private final IntegerBox dayBox = new IntegerBox() { // todo: make this numerical only

        @Override
        public void setStylePrimaryName(String style) {
            super.setStylePrimaryName(style);
            monthSelect.setStylePrimaryName(style);
            yearBox.setStylePrimaryName(style);
        }

    };
    private final ListBox monthSelect = new ListBox();
    private final IntegerBox yearBox = new IntegerBox(); // todo: make this numerical only

    public DateOfBirthField() {
        int index = 0;
        monthSelect.addItem("", Integer.toString(index));
        for (String monthString : LocaleInfo.getCurrentLocale().getDateTimeFormatInfo().monthsFull()) {
            index++;
            monthSelect.addItem(monthString, Integer.toString(index));
        }
    }

    public IntegerBox getTextBox() {
        dayBox.setWidth("50px");
        yearBox.setWidth("100px");
        dayBox.setMaxLength(2);
        yearBox.setMaxLength(4);
        this.add(dayBox);
        this.add(monthSelect);
        this.add(yearBox);
        return dayBox;
    }

    public void setDate(String dateString) {
        if (dateString != null) {
            String[] splitString = dateString.split("/");
            if (splitString.length == 3) {
                monthSelect.setSelectedIndex(Integer.parseInt(splitString[1]));
                dayBox.setValue(Integer.parseInt(splitString[0]));
                yearBox.setValue(Integer.parseInt(splitString[2]));
            }
        }
    }

    public String getValue() {
        try {
            // format the date without localisation and without timezones
            final Integer dayInteger = dayBox.getValue();
            final int monthInteger = Integer.parseInt(monthSelect.getSelectedValue());
            if (dayInteger == null) {
                return "";
            }
            final String formattedDate
                    = ((dayInteger < 10) ? "0" : "") + dayInteger
                    + "/"
                    + ((monthInteger < 10) ? "0" : "") + monthInteger
                    + "/"
                    + yearBox.getValue(); // do not pad the year so that the date validator can check it
//                DateOfBirthField.this.add(new Label(formattedDate));
            dateFormat.parseStrict(formattedDate);
            return formattedDate;
        } catch (IllegalArgumentException exception) {
//                DateOfBirthField.this.add(new Label(exception.getMessage()));
            return "";
        }
    }
}
