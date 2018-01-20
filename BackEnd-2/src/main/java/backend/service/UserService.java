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


    /*
    This function adds an event to  list events of the user
    @param user which for him the event will be added
    @param event which will be added for the user's events
    @return true , if this event is allowed to be added , otherwise false
 */
    public static boolean addEvent(AppUser user, Event event) {
		
		
        long startTime = Long.parseLong(event.getStartTime());
        long endTime = Long.parseLong(event.getEndTime());//check if this event can be added (doesn't interlap) and it's times are legal
        int size = user.getEvents().stream().filter(x ->
                ((Long.parseLong(x.getStartTime()) >=  endTime) && (Long.parseLong(x.getEndTime()) >= endTime))
                || ((Long.parseLong(x.getStartTime()) <=  startTime) && (Long.parseLong(x.getEndTime()) <= startTime))).collect(Collectors.toList()).size();
        if ((user.getEvents().size()-size) != 0) {
            return false;
        }
		//add to user's events  list
        user.addEvent(event);//since events doesn't interlap , we give the event a unique id , it's start time (easier to return it to the front when needed)
        event.setId(event.getStartTime());
        return true;
    }


    /*
 This function removes an event from the list events of user
 @param user which for him the event will be removed from his events' list
 @param eventId of the event which will be removed
 @return true , if the specific event exists in the user list event , otherwise false
  */
    public static boolean deleteEvent(AppUser user, String eventID) {

        Event event_ = null;
        boolean isExist=false;
        List<Event> tmp = user.getEvents();//temp list of the user's events
        for (Event event : user.getEvents()) {//event start time is it's id as we agreed with the front , find this event

            if (event.getStartTime().equals(eventID)) {
                event_ = event;
                isExist=true;
                break;
            }
        }

        if(!isExist){
            return  false;
        }

        tmp.remove(event_);//delete event from tmp list
        user.setEvents(tmp);//update user's list
        return true;
    }



    /*
    This function return Events which were taken place between time1 until time2
    ,and each event which will be returned through the list , will includes it's pulses
    @param user which his events will be returned
    @param time which contains the startTiming and endTiming
    @return list of events which were taken place between time1 until time2
 */
    public static List<Event> getEvents(AppUser user, TwoStrings time) {


        List<Event> events = user.getEvents();
        List<Event> filter = new ArrayList<Event>();
        for (Event event : events) {//getting all events within time period
            if (event.getStartTime().compareTo(time.getFirst()) >= 0 && event.getEndTime().compareTo(time.getSecond()) <= 0) {
                filter.add(event);
            }
        }
        List<Event> result = new ArrayList<Event>(); //filter contains the events in the given time interval
        List<Pulse> eventPulses;
		

        for (Event event : filter) {//for all the events we should get the pulses
            if (event.getPulses().size() == 0) {	//if it's pulses list is empty  , we should ask google to give us the pulses
                try {
                    eventPulses = GoogleCallParser.getPulses(user, event.getStartTime(), event.getEndTime(), MinInMs);//get the pulses in this specific time
                } catch (RefreshTokenExpiredException e) {
                    return null;
                }
                event.saveAll(eventPulses);//update the pulses for this event
                event.setAverage();
            }
        }
        System.out.println("Filter size: "+filter.size());
        return filter;
    }


    /*
        This function updates Token
        @param user which for him the token will be updated
        @param accessTokens which contains both access token and refresh token
        @return true , if the updating process passed okey, otherwise false
     */
    public static boolean updateTokens(AppUser user, TwoStrings accessTokens){
        //update token fields 
        user.setGoogleFitAccessToken(accessTokens.getFirst());
        user.setGoogleFitRefreshToken(accessTokens.getSecond());
        return true;
    }
}
