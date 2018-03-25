package com.revature.project2.users;

import com.revature.project2.events.Event;
import com.revature.project2.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class UserController {
  private final UserService userService;
  private final EventService eventService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserController(UserService userService, EventService eventService, BCryptPasswordEncoder bCryptPasswordEncoder) {

    this.userService = userService;
    this.eventService = eventService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
  @PostMapping("/users/{id}")
  public User updateUser(@PathVariable int id, @RequestBody User user) {
    return userService.save(user);
  }

  /**
   * Set whether the user is attending, not attending, or interested. If the user is bringing guests
   * , that will be included in the body as well.
   *
   * @param event_id event's id
   * @param user_id  user's id
   * @return user with changed information
   */
  @GetMapping("/events/{event_id}/user/{user_id}")
  public ResponseEntity setUser(@PathVariable int event_id, @PathVariable int user_id) {
    Event event = eventService.findByEventId(event_id).get();
    User user = userService.findByUserId(user_id).get();
    event.getAttendees().add(user);
    eventService.saveEvents(event);

    return new ResponseEntity(null, HttpStatus.OK);
  }
  @GetMapping("/events/cancel/{event_id}/user/{user_id}")
  public ResponseEntity cancelEvent(@PathVariable int event_id, @PathVariable int user_id){
    Event event = eventService.findByEventId(event_id).get();
    User user = userService.findByUserId(user_id).get();
    event.getAttendees().remove(user);
    eventService.saveEvents(event);
    return new ResponseEntity(null, HttpStatus.OK);
  }

  @GetMapping("/events/attend/{event_id}/user/{user_id}")
  public ResponseEntity isAttend(@PathVariable int event_id, @PathVariable int user_id){
    Event event = eventService.findByEventId(event_id).get();
    User user = userService.findByUserId(user_id).get();
    Map<String, Boolean> value = new HashMap<>();
    value.put("attending",event.getAttendees().contains(user));
    return new ResponseEntity(value,HttpStatus.OK);

  }


  @PostMapping("/users/{id}/password")
  public ResponseEntity updatePassword(@PathVariable int id, @RequestBody Map<String, String> pw) {
    User user = userService.findByUserId(id).get();
    String password = pw.get("password");
    String hashed = bCryptPasswordEncoder.encode(password);
    user.setPassword(hashed);
    userService.save(user);
    Map<String, String> message = new HashMap<>();
    message.put("message", "Password updated successfully");
    return new ResponseEntity(message, HttpStatus.OK);
  }
}
