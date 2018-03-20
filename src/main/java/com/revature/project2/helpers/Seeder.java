package com.revature.project2.helpers;

import com.github.javafaker.Faker;
import com.revature.project2.events.Event;
import com.revature.project2.events.EventRepository;
import com.revature.project2.users.User;
import com.revature.project2.users.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class Seeder {

  private Faker faker;
  private UserRepository userRepository;
  private EventRepository eventRepository;

  @Autowired
  public Seeder(Faker faker, UserRepository userRepository, EventRepository eventRepository) {
    this.faker = faker;
    this.userRepository = userRepository;
    this.eventRepository = eventRepository;
  }

  public User makeUser(boolean isAdmin) {
    return User.builder()
        .username(faker.name().username())
        .password(faker.internet().password())
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .email(faker.internet().safeEmailAddress())
        .dateOfBirth(new Date())
        .placeId(faker.lorem().fixedString(16))
        .isAdmin(isAdmin)
        .isFlagged(false)
        .build();
  }

  public Event makeEvent() {
    return Event.builder()
        .host(createUser(false))
        .placeId(faker.lorem().fixedString(16))
        .title(faker.gameOfThrones().character())
        .startDateTime(new Date())
        .endDateTime(new Date())
        .description(faker.lorem().sentence())
        .cost(Integer.parseInt(faker.number().digits(2)))
        .maxAttendees(Integer.parseInt(faker.number().digits(2)))
        .minimumAge(18)
        .guestsAllowed(Integer.parseInt(faker.number().digits(2)))
        .build();
  }

  public Event createEvent() {
    return eventRepository.save(makeEvent());
  }

  public User createUser(boolean isAdmin) {
    return userRepository.save(makeUser(isAdmin));
  }
}
