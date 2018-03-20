package com.revature.project2.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revature.project2.events.Event;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * beans for user
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "participant")
public class User {
  /**
   * id for user
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  /**
   * username to login to application
   */
  private String username;
  /**
   * password to login
   */
  private String password;
  /**
   * first name of user
   */
  private String firstName;
  /**
   * last name of user
   */
  private String lastName;
  /**
   * email address of user
   */
  private String email;
  /**
   * Birthday of user
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date dateOfBirth;

  /**
   * Geo-coordinates for the user
   */
  private String placeId;
  /**
   * Determine this account is admin or not
   */
  private boolean isAdmin;
  /**
   * Determine this account is flagged or not
   */
  private boolean isFlagged;
  /**
   * List of events that this user attending
   */
  @ManyToMany(mappedBy = "attendees")
  private Set<Event> events;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}


