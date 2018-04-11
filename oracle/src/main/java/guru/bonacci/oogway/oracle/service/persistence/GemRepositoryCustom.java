package guru.bonacci.oogway.oracle.service.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

/**
 * Following the spring data naming convention we define a 'custom interface'
 * called ...RepositoryCustom
 */
@Repository
public interface GemRepositoryCustom {
	
	void saveTheNewOnly(Gem... gems);
	
	Optional<Gem> consultTheOracle(String searchString);

	Optional<Gem> consultTheOracle(String searchString, String author);
	
	Optional<Gem> findRandom();
}
