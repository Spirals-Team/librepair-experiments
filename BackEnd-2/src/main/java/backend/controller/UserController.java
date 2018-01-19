package backend.controller;

import backend.DailyPulseApp;
import backend.entity.*;
import backend.googleFitApi.GoogleCallParser;
import backend.helperClasses.TwoStrings;
import backend.repository.EventRepository;
import backend.repository.UserRepository;
import backend.service.UserService;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String MinInMs = "60000";
    public UserController(UserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public boolean signUp(AppUser user) {
        try {
            if (appUserRepository.findByUsername(user.getUsername()) != null) {
                return false;
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setEvents(new ArrayList<>());
            appUserRepository.save(user);
            return true;
        }
        catch (Exception e){
            DailyPulseApp.LOGGER.info("error from backend " + e.toString());
            return false;
        }
    }

    @PostMapping("/updateGoogleFitToken")
    public boolean updateGoogleFitToken(Authentication auth, @RequestBody TwoStrings accessTokens) {
        try {
            AppUser user = appUserRepository.findByUsername(auth.getName());
       /* user.setGoogleFitAccessToken(accessTokens.getFirst());
        user.setGoogleFitRefreshToken(accessTokens.getSecond());
        */
            System.out.println(user.getUsername());
            UserService.updateTokens(user,accessTokens);
            appUserRepository.save(user);
            //System.out.println(user.getGoogleFitAccessToken());
            return true;
        }
        catch (Exception e){
            DailyPulseApp.LOGGER.info("error from backend " + e.toString());
            return false;
        }
    }

    // Frontend Team requested this
    @GetMapping("/authenticateToken")
    public Boolean authenticateToken() {
        //when calling this method with an invalid token, an error will be returned,
        //else, nothing will be returned
        return true;
    }

    @GetMapping("/private")
    public String privatee() {
        return "THIS IS PRIVATE!!";
    }

    //Aux Testing Method
    @GetMapping("/username")
    public String getUsername(Authentication auth) {
        return appUserRepository.findByUsername(auth.getName()).getName();
    }
    @PostMapping("/addEvent")
    public boolean addEvent(Authentication auth, @RequestBody Event event) {
        AppUser user = appUserRepository.findByUsername(auth.getName());
       if(!UserService.addEvent(user,event)){
           return false;
       }
        /** checking whether the event interval intersects with existed event in the EventList that **/
   /*     long startTime=Long.parseLong(event.getStartTime());
        long endTime=Long.parseLong(event.getEndTime());
        int size= user.getEvents().stream().filter(x->((Long.parseLong(x.getStartTime())>=

                startTime) && Long.parseLong(x.getStartTime())<=endTime )
                || ((Long.parseLong(x.getStartTime())<=startTime) && Long.parseLong(x.getEndTime())>startTime )
                || ((Long.parseLong(x.getStartTime())>=startTime) && Long.parseLong(x.getStartTime())<endTime )
                || ((Long.parseLong(x.getStartTime())<startTime) && Long.parseLong(x.getEndTime())>startTime)
                || ((Long.parseLong(x.getStartTime())>startTime) && Long.parseLong(x.getEndTime())<startTime)).collect(Collectors.toList()).size();
        if(size>0){
            return  false;
        }
        user.addEvent(event);
        event.setId(event.getStartTime());*/
        appUserRepository.save(user);
        return true;
    }
    //Aux Testing Method.
    @GetMapping("/getAllEvents")
    public List<Event> getAllEvents(Authentication auth) {
        return appUserRepository.findByUsername(auth.getName()).getEvents();
    }
    @PostMapping("/deleteEvent")
    public Boolean deleteEvent(Authentication auth, String eventId){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        UserService.deleteEvent(user,eventId);
        appUserRepository.save(user);
        return true;
    }
    @PostMapping("/getEvents")
    public List<Event> getEvents(Authentication auth,@RequestBody TwoStrings time) {
        AppUser user = appUserRepository.findByUsername(auth.getName());
        List<Event> filter = UserService.getEvents(user,time);
        appUserRepository.save(user);
        return filter.stream().sorted(new Comparator<Event>() {
            @Override
            public int compare(Event r1, Event r2) {
                return (r1.getId().compareTo(r2.getId()));
            }
        }).collect(Collectors.toList());
    }

    @PostMapping("/getEvent")
    public Event getEvent(Authentication auth, String id) {
        return appUserRepository.findByUsername(auth.getName()).getEvent(id);
    }
    @GetMapping("/verifyAccessToken")
    public boolean verifyAccessToken(Authentication auth){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        boolean response = GoogleCallParser.verifyAndRefresh(user);
        appUserRepository.save(user);
        return response;
    }
    //testing method
    @GetMapping("/refreshAccessToken")
    public boolean refreshAccessToken(Authentication auth){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        GoogleCallParser.refreshToken(user);
        return true;
    }
}