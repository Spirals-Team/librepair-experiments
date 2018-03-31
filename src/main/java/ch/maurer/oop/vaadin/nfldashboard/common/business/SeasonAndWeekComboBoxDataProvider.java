package ch.maurer.oop.vaadin.nfldashboard.common.business;

import java.util.ArrayList;
import java.util.List;

public final class SeasonAndWeekComboBoxDataProvider {

    private static final int WEEKS_PER_SEASON = 16;

    private SeasonAndWeekComboBoxDataProvider() {
    }

    public static List<String> getSeasonList() {
        List<String> seasonList = new ArrayList<>();
        seasonList.add("2017");
        seasonList.add("2016");
        return seasonList;
    }

    public static List<String> getWeeksList() {
        List<String> weeksList = new ArrayList<>();
        for (int i = 0; i < WEEKS_PER_SEASON; i++) {
            weeksList.add(String.valueOf(i + 1));
        }

        return weeksList;
    }
}
