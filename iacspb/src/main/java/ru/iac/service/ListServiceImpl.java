package ru.iac.service;

import org.springframework.stereotype.Service;
import ru.iac.domain.ListTable;
import ru.iac.domain.PathTable;
import ru.iac.repository.ListRepository;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Service
public class ListServiceImpl implements ListService {
    private final ListRepository listRepository;

    public ListServiceImpl(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    @Override
    public ListTable add(ListTable listTable) {
        listRepository.save(listTable);
        return listTable;
    }

    @Override
    public List<ListTable> getById(final PathTable pathTable) {
        return listRepository.findByPathTable(pathTable);
    }
}
