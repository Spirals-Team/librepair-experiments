package ch.maurer.oop.vaadin.nfldashboard.common.db;

public enum TeamNameEnum {

    ARI("ARI", "Arizona Cardinals"),
    ATL("ATL", "Atlanta Falcons"),
    BAL("BAL", "Baltimore Ravens"),
    BUF("BUF", "Buffalo Bills"),
    CAR("CAR", "Carolina Panthers"),
    CHI("CHI", "Chicago Bears"),
    CIN("CIN", "Cincinnati Bengals"),
    CLE("CLE", "Cleveland Browns"),
    DAL("DAL", "Dallas Cowboys"),
    DEN("DEN", "Denver Broncos"),
    DET("DET", "Detroit Lions"),
    GB("GB", "Green Bay Packers"),
    HOU("HOU", "Houston Texans"),
    IND("IND", "Indianapolis Colts"),
    JAX("JAX", "Jacksonville Jaguars"),
    KC("KC", "Kansas City Chiefs"),
    LAC("LAC", "Los Angeles Chargers"),
    LA("LA", "Los Angeles Rams"),
    MIA("MIA", "Miami Dolphins"),
    MIN("MIN", "Minnesota Vikings"),
    NE("NE", "New England Patriots"),
    NO("NO", "New Orleans Saints"),
    NYG("NYG", "New York Giants"),
    NYJ("NYJ", "New York Jets"),
    OAK("OAK", "Oakland Raiders"),
    PHI("PHI", "Philadelphia Eagles"),
    PIT("PIT", "Pittsburgh Steelers"),
    SF("SF", "San Fransisco 49ers"),
    SEA("SEA", "Seattle Seahawks"),
    TB("TB", "Bampa Bay Buccaneers"),
    TEN("TEN", "Tennessee Titans"),
    WAS("WAS", "Washington Redskins");

    private final String nameShort;
    private final String nameFull;

    TeamNameEnum(String nameShort, String nameFull) {
        this.nameShort = nameShort;
        this.nameFull = nameFull;
    }

    public String getNameShort() {
        return nameShort;
    }

    public String getNameFull() {
        return nameFull.isEmpty() ? "" : nameFull;
    }
}
