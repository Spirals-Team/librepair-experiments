package by.imsha.repository;

import by.imsha.domain.Mass;

import java.util.List;

/**
 * @author Alena Misan
 */
public interface MassRepository extends QuerableMongoRepository<Mass, String> {
    public Mass findByParishId(String parishId);
    public List<Mass> findByCityIdAndDeleted(String cityId, boolean deleted);
}
