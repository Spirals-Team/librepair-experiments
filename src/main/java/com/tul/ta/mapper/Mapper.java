package com.tul.ta.mapper;

public interface Mapper<E, D> {

    E mapToEntity(D dto);

    D mapToDto(E entity);
}
