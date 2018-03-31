package ch.maurer.oop.vaadin.nfldashboard.common.business;

import ch.maurer.oop.vaadin.nfldashboard.common.db.TeamNameEnum;

public final class TeamNameService {

    private TeamNameService() {
    }

    public static String getTeamNameFull(String nameShort) {
        return TeamNameEnum.valueOf(nameShort).getNameFull();
    }
}
