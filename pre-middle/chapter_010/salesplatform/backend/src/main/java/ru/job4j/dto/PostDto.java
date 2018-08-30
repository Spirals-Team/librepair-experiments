package ru.job4j.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.job4j.model.User;
import ru.job4j.model.car.Car;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * DTO for Post.
 */
public class PostDto {

    private Long id;
    private String content;
    private Double price;
    private Boolean isActive;
    private Car car;
    private User user;
    @JsonFormat(pattern = "yyyy.MM.dd 'at' HH:mm:ss")
    private Timestamp publishDate;
    private List<Long> pictures = new CopyOnWriteArrayList<>();

    public PostDto() {
    }

    public PostDto(final Long id, final String content, final Double price,
                   final Boolean isActive, final Car car, final User user,
                   final Timestamp publishDate, final List<Long> pictures) {
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

    public PostDto setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostDto setContent(final String content) {
        this.content = content;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public PostDto setPrice(final Double price) {
        this.price = price;
        return this;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public PostDto setIsActive(final Boolean active) {
        isActive = active;
        return this;
    }

    public Car getCar() {
        return car;
    }

    public PostDto setCar(final Car car) {
        this.car = car;
        return this;
    }

    public User getUser() {
        return user;
    }

    public PostDto setUser(final User user) {
        this.user = user;
        return this;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public PostDto setPublishDate(final Timestamp publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public List<Long> getPictures() {
        return pictures;
    }

    public PostDto setPictures(final List<Long> pictures) {
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
        PostDto that = (PostDto) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.content, that.content)
            && Objects.equals(this.price, that.price)
            && Objects.equals(this.isActive, that.isActive)
            && Objects.equals(this.car, that.car)
            && Objects.equals(this.user, that.user)
            && Objects.equals(this.publishDate, that.publishDate)
            && Objects.equals(this.pictures, that.pictures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, price, isActive, car, user,
            publishDate, pictures);
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
