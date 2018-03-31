package ch.maurer.oop.vaadin.nfldashboard.common.business;

import ch.maurer.oop.vaadin.nfldashboard.common.db.TeamColorEnum;

public final class TeamColorService {

    private static final String PRIMARY_COLOR_SUFFIX = "_PRIMARY";
    private static final String SECONDARY_COLOR_SUFFIX = "_SECONDARY";

    private TeamColorService() {
    }

    public static String getPrimaryColorHex(String teamAbbr) {
        return getPrimaryColor(teamAbbr).getColorValueHex();
    }

    private static TeamColorEnum getPrimaryColor(String teamAbbr) {
        TeamColorEnum primaryTeamColor = TeamColorEnum.DEFAULT_COLOR;

        if (teamAbbr != null) {
            primaryTeamColor = TeamColorEnum.valueOf(teamAbbr + PRIMARY_COLOR_SUFFIX);
        }

        return primaryTeamColor;
    }

    public static String getSecondaryColorHex(String teamAbbr) {
        return getSecondaryColor(teamAbbr).getColorValueHex();
    }

    private static TeamColorEnum getSecondaryColor(String teamAbbr) {
        TeamColorEnum secondaryTeamColor = TeamColorEnum.DEFAULT_COLOR;

        if (teamAbbr != null) {
            secondaryTeamColor = TeamColorEnum.valueOf(teamAbbr + SECONDARY_COLOR_SUFFIX);
        }
        return secondaryTeamColor;
    }
}
