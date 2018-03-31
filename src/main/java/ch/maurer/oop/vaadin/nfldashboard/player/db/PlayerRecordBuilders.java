package ch.maurer.oop.vaadin.nfldashboard.player.db;

import java.util.HashMap;
import java.util.Map;

public final class PlayerRecordBuilders {

    private PlayerRecordBuilders() {
    }

    public static PlayerRecordBuilder emptyPlayerRecordBuilder() {
        return new PlayerRecordBuilder();
    }

    public static PlayerRecord defaultPlayer() {
        return emptyPlayerRecordBuilder()
                .withId(2504211)
                .withEsbid("BRA371156")
                .withGsisPlayerId("00-0019596")
                .withName("Tom Brady")
                .withPosition("QB")
                .withTeamAbbr("NE")
                .withStats(new HashMap<>())
                .withSeasonPts(185.08)
                .withSeasonProjectedPts(315.72)
                .withWeekPts(20.48)
                .withWeekProjectedPts(19.59)
                .build();
    }

    public static class PlayerRecordBuilder {

        private PlayerRecord playerRecord;
        private int id;
        private String esbid;
        private String gsisPlayerId;
        private String name;
        private String position;
        private String teamAbbr;
        private Map<String, String> stats;
        private double seasonPts;
        private double seasonProjectedPts;
        private double weekPts;
        private double weekProjectedPts;

        public PlayerRecordBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public PlayerRecordBuilder withEsbid(String esbid) {
            this.esbid = esbid;
            return this;
        }

        public PlayerRecordBuilder withGsisPlayerId(String gsisPlayerId) {
            this.gsisPlayerId = gsisPlayerId;
            return this;
        }

        public PlayerRecordBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PlayerRecordBuilder withPosition(String position) {
            this.position = position;
            return this;
        }

        public PlayerRecordBuilder withTeamAbbr(String teamAbbr) {
            this.teamAbbr = teamAbbr;
            return this;
        }

        public PlayerRecordBuilder withStats(Map<String, String> stats) {
            this.stats = stats;
            return this;
        }

        public PlayerRecordBuilder withSeasonPts(double seasonPts) {
            this.seasonPts = seasonPts;
            return this;
        }

        public PlayerRecordBuilder withSeasonProjectedPts(double seasonProjectedPts) {
            this.seasonProjectedPts = seasonProjectedPts;
            return this;
        }

        public PlayerRecordBuilder withWeekPts(double weekPts) {
            this.weekPts = weekPts;
            return this;
        }

        public PlayerRecordBuilder withWeekProjectedPts(double weekProjectedPts) {
            this.weekProjectedPts = weekProjectedPts;
            return this;
        }

        public PlayerRecord build() {
            if (playerRecord == null) {
                playerRecord = new PlayerRecord();
                playerRecord.setId(id);
                playerRecord.setEsbid(esbid);
                playerRecord.setGsisPlayerId(gsisPlayerId);
                playerRecord.setName(name);
                playerRecord.setPosition(position);
                playerRecord.setTeamAbbr(teamAbbr);
                playerRecord.setStats(stats);
                playerRecord.setSeasonPts(seasonPts);
                playerRecord.setSeasonProjectedPts(seasonProjectedPts);
                playerRecord.setWeekPts(weekPts);
                playerRecord.setWeekProjectedPts(weekProjectedPts);
            }
            return playerRecord;
        }
    }

}
