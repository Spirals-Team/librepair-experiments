package com.revature.project2.eventpics;

import com.revature.project2.events.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "EVENTPICTURE")
public class EventPic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String img;
    @ManyToOne
    private Event event;
}
