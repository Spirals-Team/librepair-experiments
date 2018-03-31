package ch.maurer.oop.vaadin.nfldashboard.player.db;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlayerRecordRootObject {

    @JsonProperty("statType")
    private String statType;
    @JsonProperty("season")
    private String season;
    @JsonProperty("week")
    private String week;
    @JsonProperty("playerRecords")
    private List<PlayerRecord> playerRecords;

    public PlayerRecordRootObject() {
        // used by jackson
    }

    public PlayerRecordRootObject(String week) {
        this.week = week;
    }

    public PlayerRecordRootObject(String statType, String season, String week) {
        this.statType = statType;
        this.season = season;
        this.week = week;
    }

    public PlayerRecordRootObject(String statType, String season, String week, List<PlayerRecord> playerRecords) {
        // used by jackson
        this.statType = statType;
        this.season = season;
        this.week = week;
        this.playerRecords = playerRecords;
    }

    public String getStatType() {
        return statType;
    }

    public void setStatType(String statType) {
        this.statType = statType;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public List<PlayerRecord> getPlayerRecords() {
        return playerRecords;
    }

    public void setPlayerRecords(List<PlayerRecord> playerRecords) {
        this.playerRecords = playerRecords;
    }
}
