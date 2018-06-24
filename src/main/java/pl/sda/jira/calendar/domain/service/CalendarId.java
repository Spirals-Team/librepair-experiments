package pl.sda.jira.calendar.domain.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CalendarId {
    public String createId (){
        return UUID.randomUUID().toString();
    }

}
