package py.org.fundacionparaguaya.pspserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import py.org.fundacionparaguaya.pspserver.surveys.entities.PropertyAttributeEntity;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotPropertyAttributeRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rodrigovillalba on 10/26/17.
 */
@Component
public class PropertyAttributeConfig {

    @Autowired
    SnapshotPropertyAttributeRepository repo;

    @Bean
    public List<PropertyAttributeEntity> attributeEntityList() {
        return this.repo.findAll().stream()
                .filter(attr -> attr.getPropertySchemaName() != null)
                .filter(attr -> attr.getPropertySystemName() != null)
                .collect(Collectors.toList());
    }
}
