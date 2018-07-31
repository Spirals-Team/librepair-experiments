package tech.spring.structure.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import tech.spring.structure.model.StructureEntity;
import tech.spring.structure.model.repo.custom.StructureRepoCustom;

@NoRepositoryBean
public interface StructureRepo<M extends StructureEntity> extends JpaRepository<M, Long>, JpaSpecificationExecutor<M>, StructureRepoCustom<M> {

    @Override
    public void delete(M model);

}
