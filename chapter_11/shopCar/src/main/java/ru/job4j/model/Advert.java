package ru.job4j.model;

import ru.job4j.model.car.Car;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 18.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
@Entity
@Table(name = "advert")
public class Advert extends Persistent {

    private static final long serialVersionUID = 8280964074040472053L;
    private Timestamp data;
    @Column(name = "description", length = 1000)
    private String description;
    private String town;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "id_car")
    private Car car;

    public Advert(String description, String town, User user, Car car) {
        this.data = new Timestamp(System.currentTimeMillis());
        this.description = description;
        this.user = user;
        this.car = car;
        this.state = State.NEW;
        this.town = town;
    }

    public Advert(String description, String town, User user) {
        this.description = description;
        this.user = user;
        this.data = new Timestamp(System.currentTimeMillis());
        this.state = State.NEW;
        this.town = town;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Advert() {
        super();
    }
    private Advert(Long id) {
        super(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Advert{"
                + "id="
                + getId()
                + ", data="
                + data
                + ", description='"
                + description
                + '\''
                + ", state="
                + state
                + ", car="
                + car
                + '}';
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
