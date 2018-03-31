package ch.maurer.oop.vaadin.nfldashboard.common.business;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeamNameServiceTest {

    @Test
    public void assertTeamNameFull() {
        String teamNameShort = "NE";

        String teamNameFull = TeamNameService.getTeamNameFull(teamNameShort);

        assertEquals("New England Patriots", teamNameFull);
    }

}