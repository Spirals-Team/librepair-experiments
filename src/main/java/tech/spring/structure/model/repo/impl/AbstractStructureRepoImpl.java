package tech.spring.structure.model.repo.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import tech.spring.structure.model.StructureEntity;
import tech.spring.structure.model.repo.StructureRepo;
import tech.spring.structure.model.repo.custom.StructureRepoCustom;

public abstract class AbstractStructureRepoImpl<M extends StructureEntity, R extends StructureRepo<M>> implements StructureRepoCustom<M> {

    @Autowired
    protected R repo;

    @Override
    public M create(M model) {
        return repo.save(model);
    }

    @Override
    public Optional<M> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public M update(M model) {
        return repo.save(model);
    }

    @Override
    public void delete(M model) {
        Long id = model.getId();
        repo.deleteById(id);
    }

}
