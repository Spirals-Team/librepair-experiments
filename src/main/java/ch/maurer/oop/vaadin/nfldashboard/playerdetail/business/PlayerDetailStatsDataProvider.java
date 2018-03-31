package ch.maurer.oop.vaadin.nfldashboard.playerdetail.business;

import ch.maurer.oop.vaadin.nfldashboard.common.business.DataProvider;

class PlayerDetailStatsDataProvider {

    private static final String PLAYER_DETAIL_STATS_URL = "http://api.fantasy.nfl.com/v1/players/details?playerId=%s&statType=seasonStatsformat=json";

    private final DataProvider dataProvider;

    PlayerDetailStatsDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public String getPlayerDataFromApi(int id) {
        return dataProvider.getDataFromUrl(String.format(PLAYER_DETAIL_STATS_URL, id));
    }

    public String getPlayerDetailStatsUrl() {
        return PLAYER_DETAIL_STATS_URL;
    }
}
