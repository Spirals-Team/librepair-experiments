package ch.maurer.oop.vaadin.nfldashboard.playerdetail.db;

import com.fasterxml.jackson.annotation.JsonProperty;

class PlayerDetailNoteRecord {

    @JsonProperty("id")
    private String id;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("source")
    private String source;
    @JsonProperty("headline")
    private String headline;
    @JsonProperty("body")
    private String body;
    @JsonProperty("analysis")
    private String analysis;

    PlayerDetailNoteRecord() {
        // used by jackson
    }

    PlayerDetailNoteRecord(String id, String timestamp, String source, String headline, String body, String analysis) {
        this.id = id;
        this.timestamp = timestamp;
        this.source = source;
        this.headline = headline;
        this.body = body;
        this.analysis = analysis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}
