package com.meteoradesigner.dto;

import com.meteoradesigner.HasId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class - abstract base dto entity, part of implementation of Enhanced Entity Relationships (EER)
 * model.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AbstractBaseDto implements HasId {
    private Integer id = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("Dto{%s(id=%s)}", getClass().getName(), getId());
    }

}