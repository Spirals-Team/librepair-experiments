package com.revature.project2.helpers;

import com.github.javafaker.Faker;
import com.revature.project2.events.Event;
import com.revature.project2.events.EventRepository;
import com.revature.project2.users.User;
import com.revature.project2.users.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@Component
public class Seeder {

  private static final String PLACE_ID = "ChIJucrbbyNItokRbgvG4XULCY4";

  private Faker faker;
  private UserRepository userRepository;
  private EventRepository eventRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public Seeder(Faker faker, UserRepository userRepository, EventRepository eventRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.faker = faker;
    this.userRepository = userRepository;
    this.eventRepository = eventRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public User makeAdmin() {
    String rmCharacter = faker.rickAndMorty().character();
    String first = rmCharacter.split(" ")[0];
    String last = rmCharacter.split(" ").length > 1
        ? rmCharacter.split(" ")[1]
        : "Gazorpazorp";
    return User.builder()
        .username("admin")
        .password(bCryptPasswordEncoder.encode("secret"))
        .dateOfBirth(makeDOB())
        .email(makeEmail(first, last))
        .firstName(first)
        .lastName(last)
        .isAdmin(true)
        .isFlagged(false)
        .placeId(PLACE_ID)
        .build();

  }

  public User makeUser() {
    String rmCharacter = faker.rickAndMorty().character();
    String first = rmCharacter.split(" ")[0];
    String last = rmCharacter.split(" ").length > 1
        ? rmCharacter.split(" ")[1]
        : "Gazorpazorp";
    return User.builder()
        .username(makeUsername(first, last))
        .password(bCryptPasswordEncoder.encode("secret"))
        .firstName(first)
        .lastName(last)
        .email(makeEmail(first, last))
        .dateOfBirth(makeDOB())
        .placeId(PLACE_ID)
        .isAdmin(false)
        .isFlagged(false)
        .build();
  }

  public Event makeEvent(User u) {
    return Event.builder()
        .host(u)
        .placeId(PLACE_ID)
        .title(faker.rickAndMorty().character())
        .startDateTime(faker.date().future(1, TimeUnit.HOURS))
        .endDateTime(faker.date().future(20, TimeUnit.HOURS))
        .description(faker.rickAndMorty().quote())
        .cost(Integer.parseInt(faker.number().digits(2)))
        .maxAttendees(Integer.parseInt(faker.number().digits(2)))
        .minAge(makeMinAge())
        .guestsAllowed(faker.bool().bool())
        .build();
  }

  public void createEvents(int qty, int numOfUsers) {
    IntStream.range(0, qty).forEach(i -> {
      int userId = faker.number().numberBetween(0, numOfUsers) + 1;
      User u = userRepository.findById(userId).get();
      Event e = makeEvent(u);
      eventRepository.save(e);
    });
  }

  public User createUser() {
    return userRepository.save(makeUser());
  }

  private int makeMinAge() {
    int num = faker.number().numberBetween(0, 2);
    switch (num) {
      case 0:
        return 0;
      case 1:
        return 18;
      case 2:
        return 21;
      default:
        return 0;
    }
  }

  private String makeEmail(String first, String last) {
    return first + "." + last + "@example.com";
  }

  private String makeUsername(String first, String last) {
    return first + "." + last;
  }

  private Date makeDOB() {
    return faker.date().birthday(13, faker.number().numberBetween(14, 40));
  }
}
