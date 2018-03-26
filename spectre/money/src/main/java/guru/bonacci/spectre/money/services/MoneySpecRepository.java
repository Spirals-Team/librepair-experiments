package guru.bonacci.spectre.money.services;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneySpecRepository extends ElasticsearchRepository<MoneySpec, String> {
}
