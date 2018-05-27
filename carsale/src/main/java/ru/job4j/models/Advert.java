package ru.job4j.models;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Advert {
    private int id;
    private User user;
    private int idBrand;
    private int idModel;
    private String name;
    private String description;
    private Timestamp time;
    private boolean status;
    private byte[] picture;

    public Advert() {
    }

    public Advert(User user, int idBrand, int idModel, String name, String description, byte[] picture) {
        this.user = user;
        this.idBrand = idBrand;
        this.idModel = idModel;
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

    public int getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }

    public int getIdModel() {
        return idModel;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
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
                && idBrand == advert.idBrand
                && idModel == advert.idModel
                && status == advert.status
                && Objects.equals(user, advert.user)
                && Objects.equals(name, advert.name)
                && Objects.equals(description, advert.description)
                && Objects.equals(time, advert.time)
                && Arrays.equals(picture, advert.picture);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, user, idBrand, idModel, name, description, time, status);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }
}
