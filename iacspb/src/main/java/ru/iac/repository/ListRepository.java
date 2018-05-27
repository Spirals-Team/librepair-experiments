package ru.iac.repository;

import org.springframework.data.repository.CrudRepository;
import ru.iac.domain.ListTable;
import ru.iac.domain.PathTable;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public interface ListRepository extends CrudRepository<ListTable, Integer> {
    List<ListTable> findByPathTable(PathTable pathTable);
}
