package ch.maurer.oop.vaadin.nfldashboard.gamestat.business;

import ch.maurer.oop.vaadin.nfldashboard.gamestat.db.GameStatRecordRootObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

final class GameStatResponseMapper {

    private GameStatResponseMapper() {
    }

    public static GameStatRecordRootObject mapGameStatResponseToGameStatRootObject(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, GameStatRecordRootObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
