package ru.curriculum.domain.admin.user.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@EqualsAndHashCode
@Getter
@Accessors(fluent = true)
public class Role {
    @Id
    private String code;
    private String name;

    public Role() {}

    public Role(@NonNull String code, @NonNull String name) {
        this.code = code;
        this.name = name;
    }
}
