package com.isa.instaticketapi.service;


import com.isa.instaticketapi.domain.FriendRequest;
import com.isa.instaticketapi.domain.Friends;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.FriendRequestRepository;
import com.isa.instaticketapi.repository.FriendsRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.web.rest.vm.UserResource.Friend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing user.
 *
 * @author sansajn
 */
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendsRepository friendsRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;


    /**
     * Finding friends for currently logged user.
     *
     * @return
     */
    public List<Friend> findMyFriends() {
        log.debug("Started searching friends for logged user");
        User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
        List<Friends> friendsAsUsers = friendsRepository.findAllByUser(logged);
        List<Friend> friends = new ArrayList<>();
        friendsAsUsers.forEach(friend -> {
            log.debug("User {} , and email {}", friend.getFriend().getUsername(), friend.getFriend().getEmail());
            friends.add(new Friend(friend.getFriend().getUsername(), friend.getFriend().getEmail()));
        });
        return friends;
    }

    /**
     * Deleting friend by email from current user friend list
     * @param email
     */
    public void deleteFriend(String email) {
        SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).ifPresent(user -> {
            userRepository.findOneByEmailIgnoreCase(email).ifPresent(friend -> {
                Friends friends = friendsRepository.findOneByFriend(friend);
                friendsRepository.delete(friends);
            });
        });
    }

    /**
     * Sending friend request to person with email
     * @param email
     */
    public void sendFriendRequest(String email) {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFromUser(SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get());
        Optional<User> userTo = userRepository.findOneByEmailIgnoreCase(email);
        if(!userTo.isPresent()) {
            throw new IllegalArgumentException("No user found with this email");
        }
        userTo.ifPresent(user -> friendRequest.setToUser(user));

        friendRequestRepository.save(friendRequest);
    }

    /**
     * Accepting recived friend request
     * @param id
     * @return
     */
    public Optional<FriendRequest> acceptFriendRequest(Long id) {
        return friendRequestRepository.findOneById(id).map(friendRequest -> {
            User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
            if(friendRequest.getFromUser().equals(logged)) {
                friendRequest.setAccepted(true);
                Friends friends = new Friends();
                friends.setUser(logged);
                friends.setFriend(friendRequest.getToUser());
                friendsRepository.save(friends);
            }
            else
                throw new IllegalArgumentException("Your friend request doesn't exist");
            return friendRequest;
        });
    }

    /**
     * Declining friend request
     * @param id
     * @return
     */
    public Optional<FriendRequest> deleteFriendRequest(Long id) {
        return friendRequestRepository.findOneById(id).map(friendRequest -> {
            if(friendRequest.getFromUser().equals(SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get()))
                friendRequest.setDeleted(true);
            else
                throw new IllegalArgumentException("Your friend request doesn't exist");
            return friendRequest;
        });
    }



}
