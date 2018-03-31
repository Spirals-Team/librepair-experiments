package ch.maurer.oop.vaadin.nfldashboard.common.business;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeamColorServiceTest {

    @Test
    public void assertPrimaryColorHex() {
        String teamAbbr = "NE";

        String primaryColorHex = TeamColorService.getPrimaryColorHex(teamAbbr);

        assertEquals("#022650", primaryColorHex);
    }

    @Test
    public void assertSecondaryColorHex() {
        String teamAbbr = "NE";

        String secondaryColorHex = TeamColorService.getSecondaryColorHex(teamAbbr);

        assertEquals("#C60C3", secondaryColorHex);
    }

}