package com.isa.instaticketapi.service;


import com.isa.instaticketapi.domain.FriendRequest;
import com.isa.instaticketapi.domain.Friends;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.domain.identity.FriendsIdentity;
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
     * Finding friends for provided profile.
     *
     * @return
     */
    public List<Friend> findProfileFriends(String username) {
        List<Friends> friendsAsUsers = friendsRepository.findAllByUser(userRepository.findOneByUsername(username).get());
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
        friendRequest.setAccepted(false);
        friendRequest.setDeleted(false);
        Optional<User> userTo = userRepository.findOneByEmailIgnoreCase(email);
        if(!userTo.isPresent()) {
            throw new IllegalArgumentException("No user found with this email");
        }
        userTo.ifPresent(user -> friendRequest.setToUser(user));

        friendRequestRepository.save(friendRequest);
    }

    /**
     * Accepting recived friend request
     * @param email
     * @return
     */
    public Optional<FriendRequest> acceptFriendRequest(String email) {
        User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
        User userFrom = userRepository.findOneByEmailIgnoreCase(email).get();
        return friendRequestRepository.findAllByFromUserAndToUser(userFrom,logged)
                .map(friendRequest -> {
                    FriendsIdentity friendId = new FriendsIdentity(logged.getId().toString(),userFrom.getId().toString());
                    FriendsIdentity friendId1 = new FriendsIdentity(userFrom.getId().toString(),logged.getId().toString());
                    friendRequest.setAccepted(true);
                    Friends friends = new Friends(friendId,logged,userFrom);
                    Friends friends1 = new Friends(friendId1,userFrom,logged);
                    friendsRepository.save(friends);
                    friendsRepository.save(friends1);
                    return friendRequest;
        });
    }

    /**
     * Declining friend request
     * @param email
     * @return
     */
    public Optional<FriendRequest> deleteFriendRequest(String email) {
        User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
        return friendRequestRepository.findAllByFromUserAndToUser(logged,userRepository.findOneByEmailIgnoreCase(email).get())
                .map(friendRequest -> {
                    if(friendRequest.getFromUser().equals(SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get()))
                        friendRequest.setDeleted(true);
                    else if(friendRequest.getToUser().equals(SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get()))
                        friendRequest.setDeleted(true);
                    else
                        throw new IllegalArgumentException("Your friend request doesn't exist");
                    return friendRequestRepository.save(friendRequest);
                });
    }

    /**
     * Get selected user info
     * @param username
     * @return
     */
    public Optional<User> getUserProfile(String username) {
        return userRepository.findOneByUsername(username);
    }

    /**
     * Getting all request on waiting
     * @return
     */
    public Optional<FriendRequest> getFriendRequests() {
        return friendRequestRepository.findAllByToUser(SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get())
                .filter(request -> !request.getDeleted() && !request.getAccepted());
    }


}
