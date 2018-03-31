package ch.maurer.oop.vaadin.nfldashboard.league.db;

import ch.maurer.oop.vaadin.nfldashboard.player.db.Player;

import java.io.Serializable;
import java.time.LocalDate;

public class League implements Serializable {

    private Long id = null;
    private int score;
    private String name;
    private LocalDate date;
    private Player player;
    private int count;

    public League() {
        reset();
    }

    public League(int score, String name, LocalDate date, Player player, int count) {
        this.score = score;
        this.name = name;
        this.date = date;
        this.player = new Player(player);
        this.count = count;
    }

    public League(League other) {
        this(other.getScore(), other.getName(), other.getDate(), other.getPlayer(), other.getCount());
        this.id = other.getId();
    }

    public void reset() {
        this.id = null;
        this.score = 1;
        this.name = "";
        this.date = LocalDate.now();
        this.player = null;
        this.count = 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "League{" + "id=" + getId() + ", score=" + getScore() + ", name="
                + getName() + ", player=" + getPlayer() + ", date="
                + getDate() + ", count=" + getCount() + '}';
    }

}
