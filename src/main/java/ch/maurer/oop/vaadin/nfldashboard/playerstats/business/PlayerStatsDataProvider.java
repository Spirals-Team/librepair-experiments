package ch.maurer.oop.vaadin.nfldashboard.playerstats.business;

import ch.maurer.oop.vaadin.nfldashboard.common.business.DataProvider;

public class PlayerStatsDataProvider {

    private static final String PLAYER_STATS_URL = "http://api.fantasy.nfl.com/v1/players/stats?statType=seasonStats&season=%s&week=%s&format=json";

    private final DataProvider dataProvider;

    public PlayerStatsDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public String getPlayerDataFromApi(String season, String week) {
        return dataProvider.getDataFromUrl(String.format(PLAYER_STATS_URL, season, week));
    }

    public String getPlayerStatsUrl() {
        return PLAYER_STATS_URL;
    }
}
