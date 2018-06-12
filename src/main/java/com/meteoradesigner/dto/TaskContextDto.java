package com.meteoradesigner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * This @code{TaskContextDto} class declares data transfer object for @code{Context} class.
 */
@Setter
@Getter
@NoArgsConstructor
public class TaskContextDto extends AbstractNamedDto {

    @NotBlank
    private Integer userId;

    @Size(max = 6400)
    private String description;

    /**
     * The all-args constructor.
     */
    public TaskContextDto(Integer idToSet,
                          String displayNameToSet,
                          Integer userIdToSet,
                          String descriptionToSet
    ) {
        super(idToSet, displayNameToSet);
        this.userId = userIdToSet;
        this.description = descriptionToSet;
    }
}