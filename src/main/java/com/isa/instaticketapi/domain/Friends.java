package com.isa.instaticketapi.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Entity of friends.
 *
 * @author sansajn
 */
@Entity
@Table(name = "Friends")
public class Friends implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "friendId")
    private User friend;

    public Friends() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "id=" + id +
                ", user=" + user +
                ", friend=" + friend +
                '}';
    }
}
