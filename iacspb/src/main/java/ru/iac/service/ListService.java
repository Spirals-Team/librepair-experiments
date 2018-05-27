package ru.iac.service;

import ru.iac.domain.ListTable;
import ru.iac.domain.PathTable;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public interface ListService {
    ListTable add(final ListTable listTable);
    List<ListTable> getById(final PathTable pathTable);
}
