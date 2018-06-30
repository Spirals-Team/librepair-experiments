package pl.sda.jira.calendar.domain.service;

import java.util.UUID;


public class CalendarId {
    public String createId (){
        return UUID.randomUUID().toString();
    }

}
