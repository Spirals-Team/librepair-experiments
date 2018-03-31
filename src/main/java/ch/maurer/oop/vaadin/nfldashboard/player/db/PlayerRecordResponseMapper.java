package ch.maurer.oop.vaadin.nfldashboard.player.db;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class PlayerRecordResponseMapper {

    private PlayerRecordResponseMapper() {
    }

    public static PlayerRecordRootObject mapPlayerResponseToPlayerRootObject(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, PlayerRecordRootObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
