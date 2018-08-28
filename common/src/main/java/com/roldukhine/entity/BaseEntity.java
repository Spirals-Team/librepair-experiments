package com.roldukhine.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity {
    public static final long NO_EXIST_ID_ENTITY = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public boolean idEqual(BaseEntity entity) {
        return entity.getId() == id;
    }
}
