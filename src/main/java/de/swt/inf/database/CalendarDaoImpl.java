package de.swt.inf.database;

import de.swt.inf.model.Calendar;

import java.sql.Connection;

public class CalendarDaoImpl implements CalendarDao {

    private Connection connection;

    public CalendarDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Calendar getCalendar() {
        return null;
    }

    @Override
    public Calendar getCalendarByID(int calendarId) {
        return null;
    }
}
