package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    List<Parameter> findByProperty(Property property);

    @Query("select a from Parameter a where type = ?1")
    List<Parameter> findByType(String type);

    @Query("select b from Parameter b where property=?1 and type = ?2")
    List<Parameter> findByPropertyAndType(Property property, String type);

    Long countParametersById(Long id);

    @Query("select count(d) from Parameter d where id=?1 and property_id=?2")
    Long countParameterByIdAndParentId(Long parameterId, Long parentPropertyId);

    @Query("select count(e) from Parameter e where property_id=?1")
    Long countParameterByParentId(Long propertyId);
}
