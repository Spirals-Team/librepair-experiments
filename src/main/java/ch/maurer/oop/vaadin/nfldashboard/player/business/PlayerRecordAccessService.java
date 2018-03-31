package ch.maurer.oop.vaadin.nfldashboard.player.business;

import ch.maurer.oop.vaadin.nfldashboard.common.business.impl.DataProviderImpl;
import ch.maurer.oop.vaadin.nfldashboard.player.db.PlayerRecord;
import ch.maurer.oop.vaadin.nfldashboard.player.db.PlayerRecordRootObject;
import ch.maurer.oop.vaadin.nfldashboard.playerstats.business.PlayerStatsDataProvider;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

import static ch.maurer.oop.vaadin.nfldashboard.player.db.PlayerRecordResponseMapper.mapPlayerResponseToPlayerRootObject;

public final class PlayerRecordAccessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRecordAccessService.class);

    private PlayerRecordAccessService() {
    }

    public static List<PlayerRecord> getPlayersListFromApi(String statsSeason, String statsWeek) {
        PlayerRecordRootObject playerRecordRootObject = getPlayerRootObjectFromApi(statsSeason, statsWeek);

        if (playerRecordRootObject != null) {
            return playerRecordRootObject.getPlayerRecords();
        } else {
            LOGGER.warn("No players from api available!");
            return Collections.emptyList();
        }
    }

    static PlayerRecordRootObject getPlayerRootObjectFromApi(String statsSeason, String statsWeek) {
        PlayerStatsDataProvider playerStatsDataProvider = new PlayerStatsDataProvider(new DataProviderImpl());
        String jsonString = playerStatsDataProvider.getPlayerDataFromApi(statsSeason, statsWeek);
        return mapPlayerResponseToPlayerRootObject(jsonString);
    }
}
