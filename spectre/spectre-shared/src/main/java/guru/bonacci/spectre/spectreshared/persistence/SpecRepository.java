package guru.bonacci.spectre.spectreshared.persistence;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecRepository extends ElasticsearchRepository<Spec, String>, SpecRepositoryCustom {
}
