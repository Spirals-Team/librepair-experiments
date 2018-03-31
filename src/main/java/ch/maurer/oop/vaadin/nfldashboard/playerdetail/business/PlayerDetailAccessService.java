package ch.maurer.oop.vaadin.nfldashboard.playerdetail.business;

import ch.maurer.oop.vaadin.nfldashboard.common.business.impl.DataProviderImpl;
import ch.maurer.oop.vaadin.nfldashboard.player.db.PlayerRecord;
import ch.maurer.oop.vaadin.nfldashboard.playerdetail.db.PlayerDetailRecord;
import ch.maurer.oop.vaadin.nfldashboard.playerdetail.db.PlayerDetailRecordRootObject;

import static ch.maurer.oop.vaadin.nfldashboard.playerdetail.business.PlayerDetailResponseMapper.mapPlayerDetailResponseToPlayerDetailRootObject;

public final class PlayerDetailAccessService {

    private PlayerDetailAccessService() {
    }

    public static PlayerDetailRecord getPlayerDetailFromApi(PlayerRecord playerRecord) {
        PlayerDetailRecordRootObject playerDetailRecordRootObject = getPlayerDetailRootObjectFromApi(playerRecord);

        assert playerDetailRecordRootObject != null;
        return playerDetailRecordRootObject.getPlayers().get(0);
    }

    private static PlayerDetailRecordRootObject getPlayerDetailRootObjectFromApi(PlayerRecord playerRecord) {
        PlayerDetailStatsDataProvider playerDetailStatsDataProvider = new PlayerDetailStatsDataProvider(new DataProviderImpl());
        String jsonString = playerDetailStatsDataProvider.getPlayerDataFromApi(playerRecord.getId());
        return mapPlayerDetailResponseToPlayerDetailRootObject(jsonString);
    }
}
