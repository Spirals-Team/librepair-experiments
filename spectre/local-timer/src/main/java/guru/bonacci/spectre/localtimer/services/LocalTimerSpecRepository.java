package guru.bonacci.spectre.localtimer.services;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTimerSpecRepository extends ElasticsearchRepository<LocalTimerSpec, String> {
}
