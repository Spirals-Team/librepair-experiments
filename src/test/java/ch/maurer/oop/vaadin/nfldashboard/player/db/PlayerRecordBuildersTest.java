package ch.maurer.oop.vaadin.nfldashboard.player.db;

import ch.maurer.oop.vaadin.nfldashboard.player.db.PlayerRecordBuilders.PlayerRecordBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerRecordBuildersTest {

    @Test
    public void assertEmptyPlayerRecordBuilder() {
        PlayerRecordBuilder playerRecordBuilder = PlayerRecordBuilders.emptyPlayerRecordBuilder();

        assertNotNull(playerRecordBuilder);
    }

    @Test
    public void assertDefaultPlayer() {
        int id = 2504211;
        String esbid = "BRA371156";
        String gsisPlayerId = "00-0019596";
        String name = "Tom Brady";
        String position = "QB";
        String teamAbbr = "NE";
        Map<String, String> stats = new HashMap<>();
        double seasonPts = 185.08;
        double seasonProjectedPts = 315.72;
        double weekPts = 20.48;
        double weekProjectedPts = 19.59;

        PlayerRecord playerRecord = PlayerRecordBuilders.defaultPlayer();

        assertPlayerRecord(playerRecord, id, esbid, gsisPlayerId, name, position, teamAbbr, stats, seasonPts, seasonProjectedPts, weekPts, weekProjectedPts);
    }

    private void assertPlayerRecord(PlayerRecord playerRecord, int id, String esbid, String gsisPlayerId, String name, String position, String teamAbbr, Map<String, String> stats, double seasonPts, double seasonProjectedPts, double weekPts, double weekProjectedPts) {
        assertEquals(id, playerRecord.getId());
        assertEquals(esbid, playerRecord.getEsbid());
        assertEquals(gsisPlayerId, playerRecord.getGsisPlayerId());
        assertEquals(name, playerRecord.getName());
        assertEquals(position, playerRecord.getPosition());
        assertEquals(teamAbbr, playerRecord.getTeamAbbr());
        assertEquals(stats, playerRecord.getStats());
        assertEquals(seasonPts, playerRecord.getSeasonPts(), 0);
        assertEquals(seasonProjectedPts, playerRecord.getSeasonProjectedPts(), 0);
        assertEquals(weekPts, playerRecord.getWeekPts(), 0);
        assertEquals(weekProjectedPts, playerRecord.getWeekProjectedPts(), 0);
    }

}