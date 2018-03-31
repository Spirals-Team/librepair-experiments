package ch.maurer.oop.vaadin.nfldashboard.player.db;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {

    private Long id = null;

    private String name = "";

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public Player(Player other) {
        Objects.requireNonNull(other);
        this.name = other.getName();
        this.id = other.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" + getId() + ":" + getName() + '}';
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            return super.hashCode();
        }
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Player)) {
            return false;
        }
        Player other = (Player) obj;
        if (getId() == null) {
            return other.getId() == null;
        } else {
            return getId().equals(other.getId());
        }
    }
}
