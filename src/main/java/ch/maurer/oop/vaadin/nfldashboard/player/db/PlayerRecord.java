package ch.maurer.oop.vaadin.nfldashboard.player.db;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class PlayerRecord {

    @JsonProperty("id")
    private int id;
    @JsonProperty("esbid")
    private String esbid;
    @JsonProperty("gsisPlayerId")
    private String gsisPlayerId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("position")
    private String position;
    @JsonProperty("teamAbbr")
    private String teamAbbr;
    @JsonProperty("stats")
    private Map<String, String> stats;
    @JsonProperty("seasonPts")
    private double seasonPts;
    @JsonProperty("seasonProjectedPts")
    private double seasonProjectedPts;
    @JsonProperty("weekPts")
    private double weekPts;
    @JsonProperty("weekProjectedPts")
    private double weekProjectedPts;

    public PlayerRecord() {
        // used by jackson
    }

    public PlayerRecord(int id, String esbid, String gsisPlayerId, String name, String position, String teamAbbr,
                        Map<String, String> stats, double seasonPts, double seasonProjectedPts, double weekPts,
                        double weekProjectedPts) {
        this.id = id;
        this.esbid = esbid;
        this.gsisPlayerId = gsisPlayerId;
        this.name = name;
        this.position = position;
        this.teamAbbr = teamAbbr;
        this.stats = stats;
        this.seasonPts = seasonPts;
        this.seasonProjectedPts = seasonProjectedPts;
        this.weekPts = weekPts;
        this.weekProjectedPts = weekProjectedPts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEsbid() {
        return esbid;
    }

    public void setEsbid(String esbid) {
        this.esbid = esbid;
    }

    public String getGsisPlayerId() {
        return gsisPlayerId;
    }

    public void setGsisPlayerId(String gsisPlayerId) {
        this.gsisPlayerId = gsisPlayerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeamAbbr() {
        return teamAbbr;
    }

    public void setTeamAbbr(String teamAbbr) {
        this.teamAbbr = teamAbbr;
    }

    public Map<String, String> getStats() {
        return stats;
    }

    public void setStats(Map<String, String> stats) {
        this.stats = stats;
    }

    public double getSeasonPts() {
        return seasonPts;
    }

    public void setSeasonPts(double seasonPts) {
        this.seasonPts = seasonPts;
    }

    public double getSeasonProjectedPts() {
        return seasonProjectedPts;
    }

    public void setSeasonProjectedPts(double seasonProjectedPts) {
        this.seasonProjectedPts = seasonProjectedPts;
    }

    public double getWeekPts() {
        return weekPts;
    }

    public void setWeekPts(double weekPts) {
        this.weekPts = weekPts;
    }

    public double getWeekProjectedPts() {
        return weekProjectedPts;
    }

    public void setWeekProjectedPts(double weekProjectedPts) {
        this.weekProjectedPts = weekProjectedPts;
    }

}
