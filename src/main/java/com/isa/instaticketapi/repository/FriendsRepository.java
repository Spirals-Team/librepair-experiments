package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.Friends;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.domain.identity.FriendsIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Friends entity.
 *
 * @author sansajn
 */
@Repository
public interface FriendsRepository extends JpaRepository<Friends, FriendsIdentity> {

    List<Friends> findAllByUser(User u);
    Friends findOneByFriend(User u);
}
