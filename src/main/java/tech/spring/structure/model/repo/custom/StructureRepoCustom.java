package tech.spring.structure.model.repo.custom;

import java.util.Optional;

import tech.spring.structure.model.StructureEntity;

public interface StructureRepoCustom<M extends StructureEntity> {

    public M create(M model);

    public Optional<M> read(Long id);

    public M update(M model);

    public void delete(M model);

}
