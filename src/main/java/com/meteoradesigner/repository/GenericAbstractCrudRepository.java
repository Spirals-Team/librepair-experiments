package com.meteoradesigner.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

//TODO doc
public interface GenericAbstractCrudRepository<E, ID extends Serializable> {

    <S extends E> S save(S toSave);

    Optional<E> findById(ID toFind);

    List<E> findAll();

    void deleteById(ID id);
}