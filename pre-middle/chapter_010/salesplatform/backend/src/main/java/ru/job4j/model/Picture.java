package ru.job4j.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class Picture {

    private Long id;
    private byte[] data;
    private Post post;

    public Picture() {
    }

    public Picture(final Long id) {
        this.id = id;
    }

    public Picture(final byte[] data) {
        this.data = data;
    }

    public Picture(final byte[] data, final Post post) {
        this.data = data;
        this.post = post;
    }

    public Picture(final Long id, final byte[] data, final Post post) {
        this.id = id;
        this.data = data;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public Picture setId(final Long id) {
        this.id = id;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public Post getPost() {
        return post;
    }

    public Picture setPost(final Post post) {
        this.post = post;
        return this;
    }

    public Picture setData(final byte[] data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Picture that = (Picture) o;
        return Objects.equals(this.id, that.id)
            && Arrays.equals(this.data, that.data)
            && Objects.equals(this.post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, post);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("data = " + Arrays.toString(data))
//            .add("post = " + post)
            .toString();
    }
}
