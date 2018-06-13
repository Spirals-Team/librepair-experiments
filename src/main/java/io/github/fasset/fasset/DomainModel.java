/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Main fields for models used in this business doamin
 *
 * @param <U> Data type used for user identification
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class DomainModel<U> {


    @GenericGenerator(name = "sequenceGenerator", strategy = "enhanced-sequence",
        parameters = {@org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo"), @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")})
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @Id
    @Column(name = "nomenclature", updatable = false, nullable = false)
    private int id;

    @Version
    private int version;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @CreatedBy
    private U createdBy;

    @LastModifiedBy
    private U lastModifiedBy;

    public DomainModel() {
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public U getCreatedBy() {
        return createdBy;
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DomainModel<?> that = (DomainModel<?>) o;
        return id == that.id && version == that.version && Objects.equals(createdAt, that.createdAt) && Objects.equals(modifiedAt, that.modifiedAt) && Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, createdAt, modifiedAt, createdBy, lastModifiedBy);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DomainModel{");
        sb.append("nomenclature=").append(id);
        sb.append(", version=").append(version);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", modifiedAt=").append(modifiedAt);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", lastModifiedBy=").append(lastModifiedBy);
        sb.append('}');
        return sb.toString();
    }

}
