package com.isa.instaticketapi.domain.identity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class FriendsIdentity  implements Serializable {

    @NotNull
    private String userIdForKey;

    @NotNull
    private String friendIdForKey;

    public FriendsIdentity() {

    }

    public FriendsIdentity(String userId, String friendId) {
        this.userIdForKey = userId;
        this.friendIdForKey = friendId;
    }

    public String getUserId() {
        return userIdForKey;
    }

    public void setUserId(String userId) {
        this.userIdForKey = userId;
    }

    public String getFriendId() {
        return friendIdForKey;
    }

    public void setFriendId(String friendId) {
        this.friendIdForKey = friendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendsIdentity that = (FriendsIdentity) o;

        if (!userIdForKey.equals(that.userIdForKey)) return false;
        return friendIdForKey.equals(that.friendIdForKey);
    }

    @Override
    public int hashCode() {
        int result = userIdForKey.hashCode();
        result = 31 * result + friendIdForKey.hashCode();
        return result;
    }
}
