package com.meteoradesigner.model;

import com.meteoradesigner.HasId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//TODO fix all the documentation, by using this class, -es and dots.
/**
 * Class - abstract base entity, part of implementation of Enhanced Entity Relationships (EER)
 * model.
 *
 * @see <a href=http://cs-exhibitions.uni-klu.ac.at/index.php?id=431>EER</a>
 */
//Overriding id methods.
@MappedSuperclass
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class AbstractBaseEntity implements HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("Entity{%s(id=%s)}", getClass().getName(), getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractBaseEntity that = (AbstractBaseEntity) o;

        return getId() != null && getId().equals(that.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}