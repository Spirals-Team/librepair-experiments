package ch.maurer.oop.vaadin.nfldashboard.playerdetail.db;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlayerDetailRecord {

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
    @JsonProperty("status")
    private String status;
    @JsonProperty("injuryGameStatus")
    private String injuryGameStatus;
    @JsonProperty("jerseyNumber")
    private String jerseyNumber;
    @JsonProperty("notes")
    private List<PlayerDetailNoteRecord> notes;
    @JsonProperty("videos")
    private List<PlayerDetailVideoRecord> videos;
    @JsonProperty("weeks")
    private List<PlayerDetailWeekRecord> weeks;
    private int byeWeek;

    public PlayerDetailRecord() {
        // used by jackson
    }

    public PlayerDetailRecord(int id, String esbid, String gsisPlayerId, String name, String position, String teamAbbr,
                              String status, String injuryGameStatus, String jerseyNumber, List<PlayerDetailNoteRecord> notes,
                              List<PlayerDetailVideoRecord> videos, List<PlayerDetailWeekRecord> weeks) {
        this.id = id;
        this.esbid = esbid;
        this.gsisPlayerId = gsisPlayerId;
        this.name = name;
        this.position = position;
        this.teamAbbr = teamAbbr;
        this.status = status;
        this.injuryGameStatus = injuryGameStatus;
        this.jerseyNumber = jerseyNumber;
        this.notes = notes;
        this.videos = videos;
        this.weeks = weeks;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInjuryGameStatus() {
        return injuryGameStatus;
    }

    public void setInjuryGameStatus(String injuryGameStatus) {
        this.injuryGameStatus = injuryGameStatus;
    }

    public String getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(String jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public List<PlayerDetailNoteRecord> getNotes() {
        return notes;
    }

    public void setNotes(List<PlayerDetailNoteRecord> notes) {
        this.notes = notes;
    }

    public List<PlayerDetailVideoRecord> getVideos() {
        return videos;
    }

    public void setVideos(List<PlayerDetailVideoRecord> videos) {
        this.videos = videos;
    }

    public List<PlayerDetailWeekRecord> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<PlayerDetailWeekRecord> weeks) {
        this.weeks = weeks;
    }

    public int getByeWeek() {
        return byeWeek;
    }

    public void setByeWeek(int byeWeek) {
        this.byeWeek = byeWeek;
    }

}
