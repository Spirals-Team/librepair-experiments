package com.revature.project2.events;

import com.fasterxml.jackson.annotation.*;
import com.revature.project2.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @NotNull
  private User host;

  @NotNull
  private String placeId;

  @NotNull
  private String location;

  @NotNull
  private String title;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private Date startDateTime;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private Date endDateTime;

  @NotNull
  @Lob
  private String description;

  @NotNull
  private int cost;

  @NotNull
  private int maxAttendees;

  @NotNull
  private int minAge;

  @ManyToMany(fetch = FetchType.EAGER )
  @JoinTable(name = "MAP_OF_EVENTS",
          joinColumns = {@JoinColumn(name = "EVENT_ID")},
          inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
  private Set<User> attendees;
}
