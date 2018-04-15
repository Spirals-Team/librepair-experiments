package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Seat findOneById(Long id);
    ArrayList<Seat> findAllByHall(Hall hall);
    Seat findOneByHallAndAndCordXAndCordY(Hall hall,int x, int y);
    List<Seat> findAllByProjection(Projection projection);
    Seat findOneByCordXAndCordYAndProjection(int x, int y, Projection projection);

}
