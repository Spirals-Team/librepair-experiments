package com.revature.project2.bootstrap;

import com.revature.project2.events.Event;
import com.revature.project2.events.EventService;
import com.revature.project2.users.User;
import com.revature.project2.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class DataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private UserService userService;
  private EventService eventService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public DataBootstrap(UserService userService, EventService eventService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userService = userService;
    this.eventService = eventService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    createAdmin();
    createEvents();
  }

  private void createAdmin() {
    if (userService.findByUsername("admin") == null) {
      User admin = User.builder().username("admin").password("secret").email("jn@gmail.com").build();
      admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
      log.info("Created basic Administrator");
      userService.save(admin);
    }
  }
  private void createEvents(){
    User host = User.builder().username("user1").password("secret").email("jn@gmail.com").build();
    host.setPassword(bCryptPasswordEncoder.encode(host.getPassword()));
    userService.save(host);
    Date sd = new Date(2018,1,1);

    Date ed = new Date(2018,1,3);
    Event e1 = Event.builder().title("Running around with fun dance")
            .cost(40).host(host).startDateTime(sd).endDateTime(ed).build();
    Event e2 = Event.builder().title("alchol party all day long")
            .cost(40).host(host).startDateTime(sd).endDateTime(ed).build();
    Event e3 = Event.builder().title("Video game party")
            .cost(40).host(host).startDateTime(sd).endDateTime(ed).build();
    Event e4 = Event.builder().title("Nerd meet up")
            .cost(40).host(host).startDateTime(sd).endDateTime(ed).build();
    Event e5 = Event.builder().title("THere is no event event for sarcasm")
            .cost(40).host(host).startDateTime(sd).endDateTime(ed).build();

    eventService.saveEvents(e1);
    eventService.saveEvents(e2);
    eventService.saveEvents(e3);
    eventService.saveEvents(e4);
    eventService.saveEvents(e5);


  }
}
