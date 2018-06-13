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
package io.github.fasset.fasset.book;

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

/**
 * Main fields for models used to represent {@code persistentAccount} and {@code persistentEntry} items
 *
 * @param <U> Data type used for user identification
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AccountDomainModel<U> {

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

    public AccountDomainModel() {
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

        AccountDomainModel<?> that = (AccountDomainModel<?>) o;

        if (id != that.id) {
            return false;
        }
        if (version != that.version) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) {
            return false;
        }
        if (modifiedAt != null ? !modifiedAt.equals(that.modifiedAt) : that.modifiedAt != null) {
            return false;
        }
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) {
            return false;
        }
        return lastModifiedBy != null ? lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + version;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (modifiedAt != null ? modifiedAt.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
