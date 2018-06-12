package com.meteoradesigner.service;

import com.meteoradesigner.dto.AbstractBaseDto;
import com.meteoradesigner.model.AbstractBaseEntity;
import com.meteoradesigner.repository.GenericAbstractCrudRepository;
import com.meteoradesigner.util.mapper.EntityDtoMapper;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import static com.meteoradesigner.util.validator.ServiceValidatorUtil.validateNotFoundWithId;
import static java.util.stream.Collectors.toList;

//TODO documentation
//TODO implement generic Mapper by the power of reflection API and annotations.
@Transactional(readOnly = true)
public abstract class GenericAbstractCrudService
        <E extends AbstractBaseEntity, D extends AbstractBaseDto, ID extends Serializable>
        implements GenericService<E, D, ID> {

    protected abstract GenericAbstractCrudRepository<E, ID> getRepository();

    protected abstract Class<? extends D> getDtoClass();

    protected abstract EntityDtoMapper<E, D> getEntityDtoMapper();

    protected abstract Consumer<ID> getDeleteSupport();

    /**
     * {@inheritDoc}
     */
    @Override
    public D create() {
        D toCreate = null;

        try {
            toCreate = getDtoClass().newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return toCreate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public D find(ID toGet) {
        //TODO validations 1,2
        E e = validateNotFoundWithId(
                getRepository().findById(toGet).orElse(null),
                toGet);
        return getEntityDtoMapper().entityToDto(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<D> findAll() {
        //TODO validations 1,2
        List<E> allEntities = getRepository().findAll();
        return allEntities.stream()
                          .map(e -> getEntityDtoMapper().entityToDto(e))
                          .collect(toList());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public void update(D toUpdateFrom) {
        //TODO validations 1,2
        Integer id = toUpdateFrom.getId();
        E e = getEntityDtoMapper().updateEntityFromDto(validateNotFoundWithId(
                getRepository().findById((ID) id).orElse(null), id), toUpdateFrom);
        getRepository().save(e);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(ID toDelete) {
        //TODO validations 1,2
        getDeleteSupport().accept(toDelete);
        validateNotFoundWithId(
                getRepository().findById(toDelete).orElse(null) == null,
                toDelete);
    }
}