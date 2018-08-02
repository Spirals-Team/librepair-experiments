package touk.parkingmeter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import touk.parkingmeter.domain.Rate;
import java.util.Optional;

@Repository
public interface RateRepository extends CrudRepository<Rate, Long> {
    Optional<Rate> findByCurrencyAndDriverType(String currency, String driverType);
}
