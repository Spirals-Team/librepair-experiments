package com.justinfrasier.Json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.justinfrasier.https.HTTPS;
import java.util.ArrayList;

import java.util.List;

public class Json {

    private JsonObject json;

    public Json(String url) {
        String s = new HTTPS().sendGet(url);
        json = new Gson().fromJson(s, JsonObject.class);
    }

    private JsonArray getScheduleArray() {
        return json.get("Schedule").getAsJsonArray();
    }

    private String getData(int gameId, String data){
        JsonArray array = getScheduleArray();
        for (JsonElement j : array) {
            JsonObject k = (JsonObject) j;
            if (gameId == k.get("gameId").getAsInt())
                return k.get(data).getAsString();
        }
        return null;
    }

    public int currentWeek() {
        String output = json.get("currentWeek").getAsString();
        return Integer.parseInt(output);
    }

    public List<Integer> gamesInCurrentWeek(int currentWeek) {
        List<Integer> games = new ArrayList<Integer>();
        JsonArray array = getScheduleArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject j = (JsonObject) array.get(i);
            int gameWeek = j.get("gameWeek").getAsInt();
            if (gameWeek == currentWeek) {
                games.add(j.get("gameId").getAsInt());
            }
        }
        return games;
    }

    public int getGameWeek(int gameId) {
        JsonArray array = getScheduleArray();
        for (JsonElement j : array) {
            JsonObject k = (JsonObject) j;
            if (gameId == k.get("gameId").getAsInt())
                return k.get("gameWeek").getAsInt();
        }
        return -1;
    }

    public String getGameDate(int gameId) {return getData(gameId, "gameDate");}

    public String getAwayTeam(int gameId) {return getData(gameId, "awayTeam");}

    public String getHomeTeam(int gameId) {return getData(gameId, "homeTeam");}

    public String getGameTimeET(int gameId){ return getData(gameId, "gameTimeET");}

    public String getTvStation(int gameId){return getData(gameId, "tvStation");}

    public String getWinner(int gameId){return getData(gameId,"winner");}

}