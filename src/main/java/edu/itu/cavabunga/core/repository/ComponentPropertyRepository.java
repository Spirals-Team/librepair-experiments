package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.ComponentProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentPropertyRepository extends JpaRepository<ComponentProperty, Long> {
    ComponentProperty findByComponent(Component component);
}
