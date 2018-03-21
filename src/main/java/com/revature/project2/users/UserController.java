package com.revature.project2.users;

import com.revature.project2.events.Event;
import com.revature.project2.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class UserController {
  private final UserService userService;
  private final EventService eventService;

  @Autowired
  public UserController(UserService userService, EventService eventService) {

    this.userService = userService;
    this.eventService = eventService;
  }

  @GetMapping("/api/users")
  public Iterable<User> fetchAllUsers() {
    return userService.findAllUsers();
  }

//  @PostMapping("/users/create")
//  public User createUsers(User user) {
//    return userService.save(user);
//  }

  /**
   * Fetch a user by their id
   *
   * @param id users id
   * @return optional that contain user
   */
  @GetMapping("/users/{id}")
  public Optional<User> fetchUserById(@PathVariable int id) {
    return userService.findByUserId(id);
  }

  /**
   * update a user
   *
   * @param id user's id
   * @return user object
   */
  @PutMapping("/api/users/{id}")
  public User updateUser(@PathVariable int id) {
    User user = userService.findByUserId(id).get();

    return userService.save(user);
  }

  /**
   * Set whether the user is attending, not attending, or interested. If the user is bringing guests
   * , that will be included in the body as well.
   *
   * @param event_id event's id
   * @param user_id  user's id
   * @param user     user who are attending event
   * @return user with changed information
   */
  @PostMapping("/api/events/{event_id}/user/{user_id}")
  public User setUser(@PathVariable int event_id, @PathVariable int user_id, @RequestBody User user) {
    Event event = eventService.findByEventId(event_id).get();
//    event.getAttendees().add(user);
//    user.getEvents().add(event);
    eventService.saveEvents(event);
    return userService.save(user);
  }
}
