package com.isa.instaticketapi.domain;

import com.isa.instaticketapi.domain.identity.FriendsIdentity;

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

    @EmbeddedId
    private FriendsIdentity friendsIdentity;

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

    public Friends(FriendsIdentity friendsIdentity, User user, User friend) {
        this.friendsIdentity = friendsIdentity;
        this.user = user;
        this.friend = friend;
    }

    public FriendsIdentity getFriendsIdentity() {
        return friendsIdentity;
    }

    public void setFriendsIdentity(FriendsIdentity friendsIdentity) {
        this.friendsIdentity = friendsIdentity;
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
                "id=" + friendsIdentity +
                ", user=" + user +
                ", friend=" + friend +
                '}';
    }
}
