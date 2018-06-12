package com.meteoradesigner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * This @code{UserDto} class declares data transfer object for @code{User} class.
 */
@Setter
@Getter
@NoArgsConstructor
public class UserDto extends AbstractNamedDto {

    @NotBlank
    @Email
    @Size(min = 5, max = 6400)
    private String email;

    @Size(max = 6433)
    private String password;

    @NotNull
    private LocalDateTime registrationTime = LocalDateTime.now();

    /**
     * The all-args constructor.
     */
    public UserDto(Integer idToSet,
                   String displayNameToSet,
                   String emailToSet,
                   String passwordToSet,
                   LocalDateTime registrationTimeToSet) {
        super(idToSet, displayNameToSet);
        this.email = emailToSet;
        this.password = passwordToSet;
        this.registrationTime = registrationTimeToSet;
    }
}