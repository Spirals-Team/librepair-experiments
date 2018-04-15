package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the FriendRequest entity.
 *
 * @author sansajn
 */
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findOneById(Long id);
}
