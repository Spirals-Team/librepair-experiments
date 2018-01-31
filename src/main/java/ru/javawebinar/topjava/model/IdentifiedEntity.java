package ru.javawebinar.topjava.model;

import com.google.common.base.Preconditions;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import static javax.persistence.AccessType.FIELD;

/** @author danis.tazeev@gmail.com */
@MappedSuperclass
@Access(FIELD)
abstract class IdentifiedEntity {
    /*
     * 1. https://hibernate.atlassian.net/browse/HHH-3718
     * call to id getter initializes proxy when using AccessType( "field" )
     *
     * 2. https://hibernate.atlassian.net/browse/HHH-12265
     * Why Hibernate returns null while the same query in Postgres returns a row?
     */
    /** The value of {@code 0} (zero) means no ID assigned yet. */
    @Id
    @SequenceGenerator(name = "seqgen", sequenceName = "sequencer")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqgen")
    @Column(name = "id")
    private Integer id;

    public boolean isNew() { return id == null; }
    public Integer getId() { return id; }
    public IdentifiedEntity setId(Integer id) {
        Preconditions.checkArgument(id != 0, "id=0");
        if (!isNew()) {
            throw new IllegalStateException("not new");
        }
        this.id = id;
        return this;
    }

    @Override
    @SuppressWarnings("ReferenceEquality")
    public boolean equals(Object obj) {
        if (isNew()) {
            throw new IllegalStateException("new");
        }
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IdentifiedEntity that = (IdentifiedEntity)obj;
        return this.id == that.id || this.id != null && this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        if (isNew()) {
            throw new IllegalStateException("new");
        }
        return id;
    }
}
