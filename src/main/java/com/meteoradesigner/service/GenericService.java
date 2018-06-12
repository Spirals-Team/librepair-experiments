package com.meteoradesigner.service;

import com.meteoradesigner.dto.AbstractBaseDto;
import com.meteoradesigner.model.AbstractBaseEntity;

import java.io.Serializable;
import java.util.List;

//TODO doc
public interface GenericService
        <E extends AbstractBaseEntity, D extends AbstractBaseDto, ID extends Serializable> {

    D create();

    void update(D toUpdate);

    D find(ID toget);

    void delete(ID toDelete);

    List<D> findAll();
}
