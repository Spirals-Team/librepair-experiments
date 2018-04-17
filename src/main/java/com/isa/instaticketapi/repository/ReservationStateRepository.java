package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.ReservationState;
import com.isa.instaticketapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReservationStateRepository extends JpaRepository<ReservationState, Long> {

    ReservationState findOneByUserIncludedInReservation(User user);
    Optional<ReservationState> findAllByUserIncludedInReservation(User user);
}
