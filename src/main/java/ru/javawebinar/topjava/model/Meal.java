package ru.javawebinar.topjava.model;

import com.google.common.base.Preconditions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

/** @author danis.tazeev@gmail.com */
@Entity
@Table(name = "meal")
//@AttributeOverride(name = "id", column = @Column(name = "meal_id"))
public class Meal extends IdentifiedEntity {
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @PastOrPresent
    @Column(name = "\"when\"")
    private LocalDateTime when;
    @NotBlank
    @Column(name = "\"desc\"")
    private String desc;
    @Min(0)
    @Column(name = "calories")
    private int calories;

    public Meal() {}

    /**
     * @throws NullPointerException if...
     * @throws IllegalArgumentException if...
     */
    public Meal(User user, LocalDateTime when, String desc, int calories) {
        Objects.requireNonNull(user, "user");
        this.user = user;
        setWhen(when);
        setDesc(desc);
        setCalories(calories);
    }

    public User getUser() { return user; }
    public LocalDateTime getWhen() { return when; }
    public String getDesc() { return desc; }
    public int getCalories() { return calories; }

    @Override
    public Meal setId(Integer id) {
        return (Meal)super.setId(id);
    }

    // TODO: must setUser() exist?

    public Meal setWhen(LocalDateTime when) {
        Objects.requireNonNull(when, "when");
        this.when = when;
        return this;
    }

    public Meal setDesc(String desc) {
        Objects.requireNonNull(desc, "desc");
        this.desc = desc;
        return this;
    }

    public Meal setCalories(int calories) {
        Preconditions.checkArgument(calories >= 0, "calories=%s", calories);
        this.calories = calories;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '#' + getId() + "U#" + user.getId()
                + ':' + desc + '@' + when + '×' + calories;
    }
}
