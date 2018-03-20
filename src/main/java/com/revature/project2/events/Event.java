package com.revature.project2.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revature.project2.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EVENT")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User host;

  private String placeId;

  private String title;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private Date startDateTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private Date endDateTime;

  private String description;

  private int cost;

  private int maxAttendees;

  private int minimumAge;

  private int guestsAllowed;

  @ManyToMany
  @JoinTable(name = "MAP_OF_EVENTS",
      joinColumns = {@JoinColumn(name = "USER_ID")},
      inverseJoinColumns = {@JoinColumn(name = "EVENT_ID")})
  private Set<User> attendees;
}
