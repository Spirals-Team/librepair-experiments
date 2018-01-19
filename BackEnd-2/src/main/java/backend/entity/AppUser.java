package backend.entity;

import backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Document
public class AppUser {
    @Id
    private String id;
    private String username;
    private String password;
    private String name;
    private String googleFitAccessToken;
    private String googleFitRefreshToken;
    private List<Event> events;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addEvent(Event event){
        //TODO: CHECK IF EVENT ALREADY EXITS

        events.add(event);
    }

    public String getGoogleFitAccessToken() {
        return googleFitAccessToken;
    }

    public void setGoogleFitAccessToken(String googleFitAccessToken) {
        this.googleFitAccessToken = googleFitAccessToken;
    }

    public String getGoogleFitRefreshToken() {
        return googleFitRefreshToken;
    }

    public void setGoogleFitRefreshToken(String googleFitRefreshToken) {
        this.googleFitRefreshToken = googleFitRefreshToken;
    }

    public Event getEvent(String id) {
       for(Event event : events){

           if(event.getId().compareTo(id)==0){
               return event;
           }
       }
       return null;
    }

    public List<Event> getEvents() {
        return events;
    }
    public void saveAll(List<Event> e){
        events.addAll(e);
    }

    public void deleteEvent(String id){

        for(Event event:events){
            if(event.getId().compareTo(id) == 0){
                events.remove(event);
                return;
            }

        }
    }

    public void setEvents(List<Event> e) {
        events=e;
    }
}
