package com.isa.instaticketapi.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Entity of friend request.
 *
 * @author sansajn
 */
@Entity
@Table(name = "FriendRequest")
public class FriendRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fromUserId")
    private User fromUser;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "toUserId")
    private User toUser;

    @NotNull
    @Column(name = "isAccepted")
    private Boolean isAccepted;

    @NotNull
    @Column(name = "isDeleted")
    private Boolean isDeleted;

    public FriendRequest() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                ", isAccepted=" + isAccepted +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
