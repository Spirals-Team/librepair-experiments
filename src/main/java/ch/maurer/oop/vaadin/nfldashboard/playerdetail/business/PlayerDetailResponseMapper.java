package ch.maurer.oop.vaadin.nfldashboard.playerdetail.business;

import ch.maurer.oop.vaadin.nfldashboard.playerdetail.db.PlayerDetailRecordRootObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

final class PlayerDetailResponseMapper {

    private PlayerDetailResponseMapper() {
    }

    static PlayerDetailRecordRootObject mapPlayerDetailResponseToPlayerDetailRootObject(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, PlayerDetailRecordRootObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
