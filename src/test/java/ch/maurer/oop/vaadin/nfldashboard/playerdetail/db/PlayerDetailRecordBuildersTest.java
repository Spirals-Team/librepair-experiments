package ch.maurer.oop.vaadin.nfldashboard.playerdetail.db;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerDetailRecordBuildersTest {

    @Test
    public void assertEmptyPlayerDetailRecordBuilder() {
        PlayerDetailRecordBuilders.PlayerDetailRecordBuilder playerDetailRecordBuilder = PlayerDetailRecordBuilders.emptyPlayerDetailRecordBuilder();

        assertNotNull(playerDetailRecordBuilder);
    }

    @Test
    public void assertDefaultPlayerDetail() {
        int id = 2504211;
        String esbid = "BRA371156";
        String gsisPlayerId = "00-0019596";
        String name = "Tom Brady";
        String position = "QB";
        String teamAbbr = "NE";
        String status = "ACT";
        String injuryGameStatus = null;
        String jerseyNumber = "12";
        List<PlayerDetailNoteRecord> notes = newArrayList();
        List<PlayerDetailVideoRecord> videos = newArrayList();
        List<PlayerDetailWeekRecord> weeks = newArrayList();
        int byeWeek = 9;

        PlayerDetailRecord playerDetailRecord = PlayerDetailRecordBuilders.defaultPlayerDetail();

        assertPlayerDetailRecord(playerDetailRecord, id, esbid, gsisPlayerId, name, position, teamAbbr, status, injuryGameStatus, jerseyNumber, notes, videos, weeks, byeWeek);
    }

    @Test
    public void assertDefaultPlayerDetailWithoutTeam() {
        int id = 2504211;
        String esbid = "BRA371156";
        String gsisPlayerId = "00-0019596";
        String name = "Tom Brady";
        String position = "QB";
        String teamAbbr = null;
        String status = "ACT";
        String injuryGameStatus = null;
        String jerseyNumber = "12";
        List<PlayerDetailNoteRecord> notes = newArrayList();
        List<PlayerDetailVideoRecord> videos = newArrayList();
        List<PlayerDetailWeekRecord> weeks = newArrayList();
        int byeWeek = 9;

        PlayerDetailRecord playerDetailRecord = PlayerDetailRecordBuilders.defaultPlayerDetail();

        assertPlayerDetailRecord(playerDetailRecord, id, esbid, gsisPlayerId, name, position, teamAbbr, status, injuryGameStatus, jerseyNumber, notes, videos, weeks, byeWeek);
    }

    private void assertPlayerDetailRecord(PlayerDetailRecord playerDetailRecord, int id, String esbid, String gsisPlayerId, String name, String position, String teamAbbr, String status, String injuryGameStatus, String jerseyNumber, List<PlayerDetailNoteRecord> notes, List<PlayerDetailVideoRecord> videos, List<PlayerDetailWeekRecord> weeks, int byeWeek) {
        assertEquals(id, playerDetailRecord.getId());
        assertEquals(esbid, playerDetailRecord.getEsbid());
        assertEquals(gsisPlayerId, playerDetailRecord.getGsisPlayerId());
        assertEquals(name, playerDetailRecord.getName());
        assertEquals(position, playerDetailRecord.getPosition());
        assertEquals(teamAbbr, playerDetailRecord.getTeamAbbr());
        assertEquals(status, playerDetailRecord.getStatus());
        assertEquals(injuryGameStatus, playerDetailRecord.getInjuryGameStatus());
        assertEquals(jerseyNumber, playerDetailRecord.getJerseyNumber());
        assertEquals(notes, playerDetailRecord.getNotes());
        assertEquals(videos, playerDetailRecord.getVideos());
        assertEquals(weeks, playerDetailRecord.getWeeks());
        assertEquals(byeWeek, playerDetailRecord.getByeWeek());
    }

}