package com.isa.instaticketapi.web.rest.vm.UserResource;

import com.isa.instaticketapi.web.rest.vm.AbstractResponse;

import java.util.List;

public class FriendsResponse extends AbstractResponse {

    private List<Friend> friends;

    public FriendsResponse() { super(); }

    public FriendsResponse(List<Friend> friends,String message){
        super(message);
        this.friends = friends;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
