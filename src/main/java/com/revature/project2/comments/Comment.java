package com.revature.project2.comments;

import com.revature.project2.events.Event;
import com.revature.project2.users.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @ManyToOne
  @JoinColumn(name = "EVENT_ID")
  private Event event;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  private Date timeStamp;

  private String comment;

  private int upvote;

  private int downvote;
}
