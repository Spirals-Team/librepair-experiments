package com.revature.project2.bootstrap;

import com.revature.project2.events.Event;
import com.revature.project2.events.EventService;
import com.revature.project2.helpers.Seeder;
import com.revature.project2.users.User;
import com.revature.project2.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@Component
public class DataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private UserService userService;
  private EventService eventService;
  private Seeder seeder;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public DataBootstrap(UserService userService, EventService eventService, Seeder seeder, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userService = userService;
    this.seeder = seeder;
    this.eventService = eventService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    createNonGeneratedUsers();
    createNonGeneratedEvents();
  }

  private void createAdmin() {
    if (userService.findByUsername("admin") == null) {
      User admin = seeder.makeAdmin();
      log.info("Created basic Administrator");
      userService.save(admin);
    }
  }

  private void createUsers(int qty) {
    IntStream.range(0, qty).forEach(i -> {
      User u = seeder.makeUser();
      userService.save(u);
    });
  }

  private void createEvents(int qty) {
    AtomicInteger numOfUsers = new AtomicInteger();
    userService.findAllUsers().forEach(i -> numOfUsers.getAndIncrement());
    seeder.createEvents(qty, numOfUsers.get());
  }

  private void createNonGeneratedUsers() {
    userService.save(User.builder()
        .username("admin")
        .firstName("Robert")
        .lastName("Hamel")
        .email("admin@example.com")
        .password(bCryptPasswordEncoder.encode("secret"))
        .dateOfBirth(new GregorianCalendar(1996, Calendar.JUNE, 12).getTime())
        .isAdmin(true)
        .isFlagged(false)
        .build());
    userService.save(User.builder()
        .username("timneal")
        .firstName("Tim")
        .lastName("Neal")
        .password(bCryptPasswordEncoder.encode("secret"))
        .email("timneal@example.com")
        .dateOfBirth(new GregorianCalendar(1996, Calendar.JUNE, 12).getTime())
        .isAdmin(false)
        .isFlagged(false)
        .build());
    userService.save(User.builder()
        .username("kaylaweaver")
        .firstName("Kayla")
        .lastName("Weaver")
        .password(bCryptPasswordEncoder.encode("secret"))
        .email("kaylaweaver@example.com")
        .dateOfBirth(new GregorianCalendar(1996, Calendar.JUNE, 12).getTime())
        .isAdmin(false)
        .isFlagged(false)
        .build());
    userService.save(User.builder()
        .username("jeonghonoh")
        .firstName("Jeongho")
        .lastName("Noh")
        .password(bCryptPasswordEncoder.encode("secret"))
        .email("jeonghonoh@example.com")
        .dateOfBirth(new GregorianCalendar(1996, Calendar.JUNE, 12).getTime())
        .isAdmin(false)
        .isFlagged(false)
        .build());
    userService.save(User.builder()
        .username("ahmadgonzalez")
        .firstName("Ahmad")
        .lastName("Gonzalez")
        .email("ahmadgonzalez@example.com")
        .password(bCryptPasswordEncoder.encode("secret"))
        .dateOfBirth(new GregorianCalendar(1996, Calendar.JUNE, 12).getTime())
        .isAdmin(false)
        .isFlagged(false)
        .build());
  }

  private void createNonGeneratedEvents() {
    eventService.saveEvents(Event.builder()
        .host(userService.findByUserId(1).get())
        .placeId("ChIJE2tlgH43tokRe_gNuz4GrIM")
        .location("46940 Woodson Dr, Sterling, VA 20164, USA")
        .title("Bowling! @ Bowl America")
        .startDateTime(new GregorianCalendar(2018, Calendar.APRIL, 1, 18, 0).getTime())
        .endDateTime(new GregorianCalendar(2018, Calendar.APRIL, 1, 22, 0).getTime())
        .description("Celebrate April Fool's day with Beer and Bowling.")
        .cost(10)
        .maxAttendees(2)
        .minAge(21)
        .build());

    eventService.saveEvents(Event.builder()
        .host(userService.findByUserId(2).get())
        .placeId("ChIJucrbbyNItokRbgvG4XULCY4")
        .location("11730 Plaza America Dr #205, Reston, VA 20190, USA")
        .title("Project 3 Presentation")
        .startDateTime(new GregorianCalendar(2018, Calendar.MARCH, 28, 17, 30).getTime())
        .endDateTime(new GregorianCalendar(2018, Calendar.MARCH, 28, 18, 30).getTime())
        .description("Stay tuned for another exciting presentation of connect")
        .cost(1000)
        .maxAttendees(0)
        .minAge(0)
        .build());

    eventService.saveEvents(Event.builder()
        .host(userService.findByUserId(4).get())
        .placeId("ChIJObWEiAc4tokRHVlKchUVJHg")
        .location("508 Pride Ave, Herndon, VA 20170, USA")
        .title("Potluck")
        .startDateTime(new GregorianCalendar(2018, Calendar.MARCH, 30, 15, 0).getTime())
        .endDateTime(new GregorianCalendar(2018, Calendar.MARCH, 23, 18, 30).getTime())
        .description("Bring any food that you want to share!")
        .cost(0)
        .maxAttendees(20)
        .minAge(18)
        .build());

    eventService.saveEvents(Event.builder()
        .host(userService.findByUserId(3).get())
        .placeId("ChIJ5STyyx-l4IgRwYAUiiKZuTU")
        .location("Port Canaveral, FL 32920, USA")
        .title("Vacation Cruise")
        .startDateTime(new GregorianCalendar(2018, Calendar.APRIL, 15, 7, 0).getTime())
        .endDateTime(new GregorianCalendar(2018, Calendar.APRIL, 19, 16, 0).getTime())
        .description("Joyous vacation as we travel to Cozumel")
        .cost(2500)
        .maxAttendees(0)
        .minAge(18)
        .build());

    eventService.saveEvents(Event.builder()
        .host(userService.findByUserId(5).get())
        .placeId("ChIJ4_ii_eXCwogRWhqOO6GMEHU")
        .location("Beach Park, Tampa, FL, USA")
        .title("Beach Day with Mitch")
        .startDateTime(new GregorianCalendar(2018, Calendar.APRIL, 7, 9, 0).getTime())
        .endDateTime(new GregorianCalendar(2018, Calendar.APRIL, 7, 9, 0).getTime())
        .description("Let's escape this cold weather and go live it up on the beaches of Tampa...with Mitch!!")
        .cost(0)
        .maxAttendees(29)
        .minAge(0)
        .build());
  }
}
