package ru.job4j.model;

import com.google.gson.annotations.JsonAdapter;
import ru.job4j.json.PictureListSerializer;
import ru.job4j.model.car.Car;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represent Post class.
 */
public class Post {

    private Long id;
    private String content;
    private Double price;
    private Boolean isActive;
    private Car car;
    private User user;
    private Timestamp publishDate;
    @JsonAdapter(PictureListSerializer.class)
    private List<Picture> pictures = new CopyOnWriteArrayList<>();

    public Post() {
    }

    public Post(final String content, final Double price, final Boolean isActive,
                final Car car, final User user, final Timestamp publishDate,
                final List<Picture> pictures) {
        this.content = content;
        this.price = price;
        this.isActive = isActive;
        this.car = car;
        this.user = user;
        this.publishDate = publishDate;
        this.pictures = pictures;
    }

    public Post(final Long id, final String content, final Double price,
                final Boolean isActive, final Car car, final User user,
                final Timestamp publishDate, final List<Picture> pictures) {
        this.id = id;
        this.content = content;
        this.price = price;
        this.isActive = isActive;
        this.car = car;
        this.user = user;
        this.publishDate = publishDate;
        this.pictures = pictures;
    }

    public Long getId() {
        return id;
    }

    public Post setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(final String content) {
        this.content = content;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Post setPrice(final Double price) {
        this.price = price;
        return this;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Post setIsActive(final Boolean active) {
        isActive = active;
        return this;
    }

    public Car getCar() {
        return car;
    }

    public Post setCar(final Car car) {
        this.car = car;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Post setUser(final User user) {
        this.user = user;
        return this;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public Post setPublishDate(final Timestamp publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public Post setPictures(final List<Picture> pictures) {
        this.pictures = pictures;
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
        Post that = (Post) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.content, that.content)
            && Objects.equals(this.price, that.price)
            && Objects.equals(this.isActive, that.isActive)
            && Objects.equals(this.car, that.car)
            && Objects.equals(this.user, that.user)
            && Objects.equals(this.publishDate, that.publishDate)
            && this.pictures != null
            ? this.pictures.containsAll(that.pictures) : that.pictures == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, price, isActive, car, user, publishDate,
            pictures);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("content = " + content)
            .add("price = " + price)
            .add("isActive = " + isActive)
            .add("car = " + car)
            .add("user = " + user)
            .add("publishDate = " + publishDate)
            .add("pictures = " + pictures)
            .toString();
    }
}
