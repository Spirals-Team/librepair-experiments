package guru.bonacci.spectre.sentiment.services;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentimentSpecRepository extends ElasticsearchRepository<SentimentSpec, String> {
}
