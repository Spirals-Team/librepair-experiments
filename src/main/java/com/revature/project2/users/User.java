package com.revature.project2.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.revature.project2.events.Event;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * beans for user
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"events", "hostedEvents"})
@ToString(exclude = {"events", "hostedEvents"})
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
  @NotNull
  @Size(min = 4)
  @Column(unique = true)
  private String username;
  /**
   * password to login
   */
  @NotNull
  @Size(min =6)
  private String password;
  /**
   * first name of user
   */
  @NotNull
  private String firstName;
  /**
   * last name of user
   */
  @NotNull
  private String lastName;
  /**
   * email address of user
   */
  @NotNull
  @Email
  @Column(unique = true)
  private String email;
  /**
   * Birthday of user
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull
  private Date dateOfBirth;

  /**
   * Determine this account is admin or not
   */
  private boolean isAdmin;
  /**
   * Determine this account is flagged or not
   */
  private boolean isFlagged;

  @OneToMany(mappedBy = "host")
  @JsonIgnoreProperties(value = "host", allowSetters = true)
  private Set<Event> hostedEvents;

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


