package ch.maurer.oop.vaadin.nfldashboard.gamestat.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "stats"
})
public class GameStatRecordRootObject {

    @JsonProperty("stats")
    private List<GameStatRecord> stats = null;

    @JsonProperty("stats")
    public List<GameStatRecord> getStats() {
        return stats;
    }

    @JsonProperty("stats")
    public void setStats(List<GameStatRecord> stats) {
        this.stats = stats;
    }
}
