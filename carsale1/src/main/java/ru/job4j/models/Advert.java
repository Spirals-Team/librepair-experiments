package ru.job4j.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Entity(name = "advert")
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_brand")
    private Brand brand;


    @ManyToOne
    @JoinColumn(name = "id_model")
    private Model model;

    private String name;

    private String description;

    @Column(name = "timecreated")
    private Timestamp time;

    private boolean status;

    private byte[] picture;

    public Advert() {
    }

    public Advert(User user, Brand brand, Model model, String name, String description, byte[] picture) {
        this.user = user;
        this.brand = brand;
        this.model = model;
        this.name = name;
        this.description = description;
        this.time = new Timestamp(System.currentTimeMillis());
        this.status = true;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public byte[] getPicture() {
        return picture;
    }

    public String getPic() {
        return new String(getPicture());
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Advert advert = (Advert) o;
        return id == advert.id
                && status == advert.status
                && Objects.equals(user, advert.user)
                && Objects.equals(brand, advert.brand)
                && Objects.equals(model, advert.model)
                && Objects.equals(name, advert.name)
                && Objects.equals(description, advert.description)
                && Objects.equals(time, advert.time)
                && Arrays.equals(picture, advert.picture);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, user, brand, model, name, description, time, status);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }
}
