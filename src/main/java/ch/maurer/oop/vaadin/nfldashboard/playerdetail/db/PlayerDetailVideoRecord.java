package ch.maurer.oop.vaadin.nfldashboard.playerdetail.db;

import com.fasterxml.jackson.annotation.JsonProperty;

class PlayerDetailVideoRecord {

    @JsonProperty("id")
    private String id;
    @JsonProperty("smallPhotoUrl")
    private String smallPhotoUrl;
    @JsonProperty("mediumPhotoUrl")
    private String mediumPhotoUrl;
    @JsonProperty("playerIds")
    private String[] playerIds;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("playTimeOfDay")
    private Object playTimeOfDay;
    @JsonProperty("gameDescription")
    private Object gameDescription;
    @JsonProperty("gameClock")
    private Object gameClock;
    @JsonProperty("gamePlayId")
    private Object gamePlayId;

    PlayerDetailVideoRecord() {
        // used by jackson
    }

    PlayerDetailVideoRecord(String id, String smallPhotoUrl, String mediumPhotoUrl, String[] playerIds, String title,
                            String description, String timestamp, Object playTimeOfDay, Object gameDescription,
                            Object gameClock, Object gamePlayId) {
        this.id = id;
        this.smallPhotoUrl = smallPhotoUrl;
        this.mediumPhotoUrl = mediumPhotoUrl;
        this.playerIds = playerIds;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.playTimeOfDay = playTimeOfDay;
        this.gameDescription = gameDescription;
        this.gameClock = gameClock;
        this.gamePlayId = gamePlayId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmallPhotoUrl() {
        return smallPhotoUrl;
    }

    public void setSmallPhotoUrl(String smallPhotoUrl) {
        this.smallPhotoUrl = smallPhotoUrl;
    }

    public String getMediumPhotoUrl() {
        return mediumPhotoUrl;
    }

    public void setMediumPhotoUrl(String mediumPhotoUrl) {
        this.mediumPhotoUrl = mediumPhotoUrl;
    }

    public String[] getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(String[] playerIds) {
        this.playerIds = playerIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getPlayTimeOfDay() {
        return playTimeOfDay;
    }

    public void setPlayTimeOfDay(Object playTimeOfDay) {
        this.playTimeOfDay = playTimeOfDay;
    }

    public Object getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(Object gameDescription) {
        this.gameDescription = gameDescription;
    }

    public Object getGameClock() {
        return gameClock;
    }

    public void setGameClock(Object gameClock) {
        this.gameClock = gameClock;
    }

    public Object getGamePlayId() {
        return gamePlayId;
    }

    public void setGamePlayId(Object gamePlayId) {
        this.gamePlayId = gamePlayId;
    }
}
