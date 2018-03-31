package ch.maurer.oop.vaadin.nfldashboard.playerdetail.db;

import com.fasterxml.jackson.annotation.JsonProperty;

import static ch.maurer.oop.vaadin.nfldashboard.common.ObjectUtil.isFalse;

public class PlayerDetailWeekRecord {

    @JsonProperty("id")
    private int id;
    @JsonProperty("opponent")
    private Object opponent;
    @JsonProperty("gameResult")
    private Object gameResult;
    @JsonProperty("gameDate")
    private Object gameDate;
    @JsonProperty("stats")
    private Object stats;
    private boolean isByeWeek = false;

    public PlayerDetailWeekRecord() {
        // used by jackson
    }

    public PlayerDetailWeekRecord(int id, Object opponent, Object gameResult, Object gameDate, Object stats) {
        this.id = id;
        this.opponent = opponent;
        this.gameResult = gameResult;
        this.gameDate = gameDate;
        this.stats = stats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getOpponent() {
        return isFalse(opponent) ? "" : opponent;
    }

    public void setOpponent(Object opponent) {
        this.opponent = opponent;
    }

    public Object getGameResult() {
        return isFalse(gameResult) ? "" : gameResult;
    }

    public void setGameResult(Object gameResult) {
        this.gameResult = gameResult;
    }

    public Object getGameDate() {
        return isFalse(gameDate) ? "" : gameDate;
    }

    public void setGameDate(Object gameDate) {
        this.gameDate = gameDate;
    }

    public Object getStats() {
        return stats;
    }

    public void setStats(Object stats) {
        this.stats = stats;
    }

    public boolean isByeWeek() {
        if (isFalse(getGameDate()) && isFalse(getOpponent()) && isFalse(getGameResult())) {
            isByeWeek = true;
        }
        return isByeWeek;
    }

    public boolean isWin() {
        return gameResult.toString().contains("Win");
    }

    public boolean isLoss() {
        return gameResult.toString().contains("Loss");
    }
}
