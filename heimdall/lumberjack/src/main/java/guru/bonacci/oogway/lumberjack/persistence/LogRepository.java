package guru.bonacci.oogway.lumberjack.persistence;

import java.time.Instant;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<Log, String> {

	List<Log> findByMomentBetween(Instant from, Instant until);

	long countByMomentBetween(Instant from, Instant until);
	
	List<Log> findByApiKeyAndMomentBetween(String apiKey, Instant from, Instant until);

	long countByApiKeyAndMomentBetween(String apiKey, Instant from, Instant until);
}
