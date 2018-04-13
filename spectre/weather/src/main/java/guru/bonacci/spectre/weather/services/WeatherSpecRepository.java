package guru.bonacci.spectre.weather.services;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherSpecRepository extends ElasticsearchRepository<WeatherSpec, String> {
}
