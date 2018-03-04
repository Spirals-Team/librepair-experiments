package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    List<Component> findByOwner(Participant owner);

    List<Component> findByParent(Component component);

    @Query("select c from Component c where parent=?1 and type=?2")
    List<Component> findByParentAndType(Component component, String type);

    @Query("select b from Component b where type = ?1")
    List<Component> findByType(String type);

    @Query("select a from Component a where owner=?1 and type = ?2")
    List<Component> findByOwnerAndType(Participant owner, String type);

    Long countComponentById(Long componentId);

    Long countComponentByIdAndOwner(Long componentId, Participant owner);

    @Query("select count(d) from Component d where id=?1 and owner=?2 and parent_id=?3 and type=?4")
    Long countComponentByIdAndOwnerAndType(Long componentId, Participant owner, Long parentComponentId, String componentType);

    @Query("select count(f) from Component f where parent_id=?1")
    Long countComponentByParentId(Long componentParentId);

    @Query("select count(e) from Component e where parent_id=?1 and type=?2")
    Long countComponentByParentIdAndType(Long parentId, String componentType);

    @Query("select count(h) from Component h where owner=?1 and type=?2")
    Long countComponentByOwnerAndType(Participant owner, String componentType);


}
