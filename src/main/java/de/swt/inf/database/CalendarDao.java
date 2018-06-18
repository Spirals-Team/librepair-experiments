package de.swt.inf.database;

import de.swt.inf.model.Calendar;

import java.sql.Connection;

public interface CalendarDao {

    public abstract Calendar getCalendar();


    public abstract Calendar getCalendarByID(int calendarId);
}
