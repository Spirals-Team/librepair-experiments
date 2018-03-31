package ch.maurer.oop.vaadin.nfldashboard.gamestat.business;

import ch.maurer.oop.vaadin.nfldashboard.common.business.DataProvider;

class GameStatDataProvider {

    private static final String GAME_STAT_URL = "http://api.fantasy.nfl.com/v1/game/stats";

    private final DataProvider dataProvider;

    GameStatDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public String getGameStatDataFromApi() {
        return dataProvider.getDataFromUrl(GAME_STAT_URL);
    }
}
