package com.isa.instaticketapi.web.rest.vm.UserResource;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestsResponse {

    private List<Friend> requests = new ArrayList<>();

    public FriendRequestsResponse() {

    }

    public FriendRequestsResponse(List<Friend> requests) {
        this.requests = requests;
    }

    public List<Friend> getRequests() {
        return requests;
    }

    public void setRequests(List<Friend> requests) {
        this.requests = requests;
    }
}
