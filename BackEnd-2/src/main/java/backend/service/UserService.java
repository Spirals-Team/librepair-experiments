package backend.service;

import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.Pulse;
import backend.entity.RefreshTokenExpiredException;
import backend.googleFitApi.GoogleCallParser;
import backend.helperClasses.TwoStrings;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private static final String MinInMs = "60000";

    public static boolean addEvent(AppUser user, Event event) {
        long startTime = Long.parseLong(event.getStartTime());
        long endTime = Long.parseLong(event.getEndTime());
        int size = user.getEvents().stream().filter(x ->
                ((Long.parseLong(x.getStartTime()) >=  endTime) && (Long.parseLong(x.getEndTime()) >= endTime))
                || ((Long.parseLong(x.getStartTime()) <=  startTime) && (Long.parseLong(x.getEndTime()) <= startTime))).collect(Collectors.toList()).size();
        if ((user.getEvents().size()-size) != 0) {
            return false;
        }
        user.addEvent(event);
        event.setId(event.getStartTime());
        return true;
    }

    public static boolean deleteEvent(AppUser user, String eventID) {

        Event event_ = null;
        boolean isExist=false;
        List<Event> tmp = user.getEvents();
        for (Event event : user.getEvents()) {
            if (event.getStartTime().equals(eventID)) {
                event_ = event;
                isExist=true;
                break;
            }
        }

        if(!isExist){
            return  false;
        }
        tmp.remove(event_);
        user.setEvents(tmp);
        return true;
    }


    public static List<Event> getEvents(AppUser user, TwoStrings time) {


        List<Event> events = user.getEvents();
        List<Event> filter = new ArrayList<Event>();
        System.out.println("Events number: "+events.size());
        //getting all events within time period
        for (Event event : events) {
            System.out.println("requested start time: "+time.getFirst()+" requested end time: "+time.getSecond());
            System.out.println("    event start time: "+event.getStartTime()+"     event end time: "+event.getEndTime());
            System.out.println("THE RESULT: " + (event.getStartTime().compareTo(time.getFirst()) >= 0 )+ " "+" " +( event.getEndTime().compareTo(time.getSecond()) <= 0));
            if (event.getStartTime().compareTo(time.getFirst()) >= 0 && event.getEndTime().compareTo(time.getSecond()) <= 0) {
                filter.add(event);
            }
        }
        //filter contains the events in the given time
        List<Event> result = new ArrayList<Event>();
        List<Pulse> eventPulses;
        System.out.println("Filter size: "+filter.size());
        for (Event event : filter) {
            if (event.getPulses().size() == 0) {
                try {
                    eventPulses = GoogleCallParser.getPulses(user, event.getStartTime(), event.getEndTime(), MinInMs);
                } catch (RefreshTokenExpiredException e) {
                    return null;
                }
                System.out.println("Pulses size: "+eventPulses.size());
                event.saveAll(eventPulses);
                event.setAverage();
            }
        }
        System.out.println("Filter size: "+filter.size());
        return filter;
    }
    public static boolean updateTokens(AppUser user, TwoStrings accessTokens){
        System.out.println(accessTokens.getFirst());
        System.out.println(accessTokens.getSecond());
        user.setGoogleFitAccessToken(accessTokens.getFirst());
        user.setGoogleFitRefreshToken(accessTokens.getSecond());
        return true;
    }
}
