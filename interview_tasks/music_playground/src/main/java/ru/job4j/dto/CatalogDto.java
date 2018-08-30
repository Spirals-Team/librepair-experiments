package ru.job4j.dto;

import java.util.Objects;
import java.util.StringJoiner;

public class CatalogDto {

    private Long id;
    private Long userId;
    private Long genreId;

    public CatalogDto() {
    }

    public CatalogDto(final Long userId, final Long genreId) {
        this.userId = userId;
        this.genreId = genreId;
    }

    public CatalogDto(final Long id, final Long userId, final Long genreId) {
        this.id = id;
        this.userId = userId;
        this.genreId = genreId;
    }

    public Long getId() {
        return id;
    }

    public CatalogDto setId(final Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public CatalogDto setUserId(final Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getGenreId() {
        return genreId;
    }

    public CatalogDto setGenreId(final Long genreId) {
        this.genreId = genreId;
        return this;
    }

    public boolean isEmpty() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CatalogDto that = (CatalogDto) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.userId, that.userId)
            && Objects.equals(this.genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, genreId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("userId = " + userId)
            .add("genreId = " + genreId)
            .toString();
    }
}
