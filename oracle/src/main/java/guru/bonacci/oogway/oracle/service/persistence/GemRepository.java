package guru.bonacci.oogway.oracle.service.persistence;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Following the spring data naming convention we define a 'generic interface'
 * called ...Repository
 */
@Repository
public interface GemRepository extends ElasticsearchRepository<Gem, String>, GemRepositoryCustom {
}
