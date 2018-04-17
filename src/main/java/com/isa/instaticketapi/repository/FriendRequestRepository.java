package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.FriendRequest;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.web.rest.vm.UserResource.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the FriendRequest entity.
 *
 * @author sansajn
 */
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findAllByFromUserAndToUser(User userFrom,User userTo);
    Optional<FriendRequest> findAllByToUser(User user);
}
