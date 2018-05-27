package ru.iac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iac.domain.PathTable;
import ru.iac.repository.PathRepository;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Service
public class PathServiceImpl implements PathService {
    private final PathRepository repository;

    @Autowired
    public PathServiceImpl(PathRepository repository) {
        this.repository = repository;
    }

    @Override
    public PathTable add(PathTable pathTable) {
        repository.save(pathTable);
        return pathTable;
    }

    @Override
    public List<PathTable> getAll() {
        return (List<PathTable>) repository.findAllByOrderByTimeDesc();
    }

    @Override
    public PathTable findById(int id) {
        return repository.findById(id).get();
    }
}
