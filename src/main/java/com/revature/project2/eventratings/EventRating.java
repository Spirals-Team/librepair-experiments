package com.revature.project2.eventratings;

import com.revature.project2.events.Event;
import com.revature.project2.users.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "EVENT_RATING")
public class EventRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "EVENT_ID")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private int rating;

    private String comment;


}
