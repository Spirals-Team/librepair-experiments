package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByComponent(edu.itu.cavabunga.core.entity.Component component);

    @Query("select a from Property a where type = ?1")
    List<Property> findByType(String type);

    @Query("select b from Property b where component=?1 and type =?2")
    List<Property> findByComponentAndType(edu.itu.cavabunga.core.entity.Component component, String type);

    Long countPropertiesById(Long id);

    @Query("select count(c) from Property c where id=?1 and component_id=?2")
    Long countPropertyByIdAndParentId(Long propertyId, Long parentComponentId);

    @Query("select count(d) from Property d where component_id=?1")
    Long countPropertyByParentId(Long componentId);


}
