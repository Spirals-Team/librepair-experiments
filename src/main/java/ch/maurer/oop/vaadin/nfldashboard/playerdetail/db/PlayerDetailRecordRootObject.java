package ch.maurer.oop.vaadin.nfldashboard.playerdetail.db;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlayerDetailRecordRootObject {

    @JsonProperty("players")
    private List<PlayerDetailRecord> players;

    public PlayerDetailRecordRootObject() {
        // used by jackson
    }

    public PlayerDetailRecordRootObject(List<PlayerDetailRecord> players) {
        this.players = players;
    }

    public List<PlayerDetailRecord> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDetailRecord> players) {
        this.players = players;
    }
}
