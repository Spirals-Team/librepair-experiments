package touk.parkingmeter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import touk.parkingmeter.domain.Ticket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    Optional<Ticket> findByPlateNumber(String plateNumber);
    List<Ticket> findAllByStartBetween(LocalDateTime start, LocalDateTime end);
}
