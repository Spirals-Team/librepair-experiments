package ch.maurer.oop.vaadin.nfldashboard.playerdetail.db;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public final class PlayerDetailRecordBuilders {

    private PlayerDetailRecordBuilders() {
    }

    public static PlayerDetailRecordBuilder emptyPlayerDetailRecordBuilder() {
        return new PlayerDetailRecordBuilder();
    }

    public static PlayerDetailRecord defaultPlayerDetail() {
        return emptyPlayerDetailRecordBuilder()
                .withId(2504211)
                .withEsbid("BRA371156")
                .withGsisPlayerId("00-0019596")
                .withName("Tom Brady")
                .withPosition("QB")
                .withTeamAbbr("NE")
                .withStatus("ACT")
                .withInjuryGameStatus(null)
                .withJerseyNumber("12")
                .withNotes(newArrayList())
                .withVideos(newArrayList())
                .withWeeks(newArrayList())
                .withByeWeek(9)
                .build();
    }

    public static PlayerDetailRecord defaultPlayerDetailWithoutTeam() {
        return emptyPlayerDetailRecordBuilder()
                .withId(2504211)
                .withEsbid("BRA371156")
                .withGsisPlayerId("00-0019596")
                .withName("Tom Brady")
                .withPosition("QB")
                .withTeamAbbr(null)
                .withStatus("ACT")
                .withInjuryGameStatus(null)
                .withJerseyNumber("12")
                .withNotes(newArrayList())
                .withVideos(newArrayList())
                .withWeeks(newArrayList())
                .withByeWeek(9)
                .build();
    }

    public static class PlayerDetailRecordBuilder {

        private PlayerDetailRecord playerDetailRecord;
        private int id;
        private String esbid;
        private String gsisPlayerId;
        private String name;
        private String position;
        private String teamAbbr;
        private String status;
        private String injuryGameStatus;
        private String jerseyNumber;
        private List<PlayerDetailNoteRecord> notes;
        private List<PlayerDetailVideoRecord> videos;
        private List<PlayerDetailWeekRecord> weeks;
        private int byeWeek;

        public PlayerDetailRecordBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public PlayerDetailRecordBuilder withEsbid(String esbid) {
            this.esbid = esbid;
            return this;
        }

        public PlayerDetailRecordBuilder withGsisPlayerId(String gsisPlayerId) {
            this.gsisPlayerId = gsisPlayerId;
            return this;
        }

        public PlayerDetailRecordBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PlayerDetailRecordBuilder withPosition(String position) {
            this.position = position;
            return this;
        }

        public PlayerDetailRecordBuilder withTeamAbbr(String teamAbbr) {
            this.teamAbbr = teamAbbr;
            return this;
        }

        public PlayerDetailRecordBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public PlayerDetailRecordBuilder withInjuryGameStatus(String injuryGameStatus) {
            this.injuryGameStatus = injuryGameStatus;
            return this;
        }

        public PlayerDetailRecordBuilder withJerseyNumber(String jerseyNumber) {
            this.jerseyNumber = jerseyNumber;
            return this;
        }

        public PlayerDetailRecordBuilder withNotes(List<PlayerDetailNoteRecord> notes) {
            this.notes = notes;
            return this;
        }

        public PlayerDetailRecordBuilder withVideos(List<PlayerDetailVideoRecord> videos) {
            this.videos = videos;
            return this;
        }

        public PlayerDetailRecordBuilder withWeeks(List<PlayerDetailWeekRecord> weeks) {
            this.weeks = weeks;
            return this;
        }

        public PlayerDetailRecordBuilder withByeWeek(int byeWeek) {
            this.byeWeek = byeWeek;
            return this;
        }

        public PlayerDetailRecord build() {
            if (playerDetailRecord == null) {
                playerDetailRecord = new PlayerDetailRecord();
                playerDetailRecord.setId(id);
                playerDetailRecord.setEsbid(esbid);
                playerDetailRecord.setGsisPlayerId(gsisPlayerId);
                playerDetailRecord.setName(name);
                playerDetailRecord.setPosition(position);
                playerDetailRecord.setTeamAbbr(teamAbbr);
                playerDetailRecord.setStatus(status);
                playerDetailRecord.setInjuryGameStatus(injuryGameStatus);
                playerDetailRecord.setJerseyNumber(jerseyNumber);
                playerDetailRecord.setNotes(notes);
                playerDetailRecord.setVideos(videos);
                playerDetailRecord.setWeeks(weeks);
                playerDetailRecord.setByeWeek(byeWeek);
            }
            return playerDetailRecord;
        }
    }

}
