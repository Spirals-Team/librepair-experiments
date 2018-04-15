package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.ReservationInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationInvitationRepository extends JpaRepository<ReservationInvitation, Long> {
}
