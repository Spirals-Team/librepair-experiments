package ru.job4j.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class MusicType represent different music genres.
 */
public class MusicType {

    private Long id;
    private String genre;

    public MusicType() {
    }

    public MusicType(final String genre) {
        this.genre = genre;
    }

    public MusicType(final Long id, final String genre) {
        this.id = id;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public MusicType setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public MusicType setGenre(final String genre) {
        this.genre = genre;
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
        MusicType that = (MusicType) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.genre, that.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genre);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("genre = " + genre)
            .toString();
    }
}
