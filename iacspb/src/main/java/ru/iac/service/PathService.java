package ru.iac.service;

import ru.iac.domain.PathTable;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public interface PathService {
    PathTable add(final PathTable pathTable);
    List<PathTable> getAll();
    PathTable findById(final int id);
}
