package com.revature.project2.users;

import com.revature.project2.events.Event;
import com.revature.project2.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/api")
public class UserController {
  private final UserService userService;
  private final EventService eventService;

  @Autowired
  public UserController(UserService userService, EventService eventService) {

    this.userService = userService;
    this.eventService = eventService;
  }

  @GetMapping("/users")
  public Iterable<User> fetchAllUsers() {
    return userService.findAllUsers();
  }

//  @PostMapping("/users/create")
//  public User createUsers(User user) {
//    return userService.save(user);
//  }

  /**
   * Fetch a user by their id
   * @param id users id
   * @return optional that contain user
   */
  @GetMapping("/users/{id}")
  public Optional<User> fetchUserById(@PathVariable int id) {
    return userService.findByUserId(id);
  }

  /**
   * update a user
   * @param id user's id
   * @return user object
   */
  @PutMapping("/users/{id}")
  public User updateUser(@PathVariable int id) {

    User user = userService.findByUserId(id).get();

    return userService.save(user);
  }

  /**
   * Set whether the user is attending, not attending, or interested. If the user is bringing guests
   * , that will be included in the body as well.
   * @param event_id event's id
   * @param user_id user's id
   * @return user with changed information
   */
  @PostMapping("/events/{event_id}/user/{user_id}")
  public ResponseEntity setUser(@PathVariable int event_id, @PathVariable int user_id) {
    Event event = eventService.findByEventId(event_id).get();
    User user = userService.findByUserId(user_id).get();
    event.getAttendees().add(user);
    eventService.saveEvents(event);

    return new ResponseEntity(null, HttpStatus.OK);
  }

}
