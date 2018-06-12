package com.meteoradesigner.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Class - abstract named dto, part of implementation of Enhanced Entity Relationships (EER)
 * model.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class AbstractNamedDto extends AbstractBaseDto {

    @NotBlank
    @Size(min = 1, max = 333)
    private String displayName;

    /**
     * The all-args constructor.
     */
    AbstractNamedDto(Integer idToSet,
                     String nameToSet) {
        super(idToSet);
        this.displayName = nameToSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("NamedDto{%s(displayName=%s)}" + getClass().getName(), displayName);
    }
}
