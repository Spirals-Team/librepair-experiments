package ch.maurer.oop.vaadin.nfldashboard.player.business;

import ch.maurer.oop.vaadin.nfldashboard.player.db.PlayerRecord;

public final class PlayerRecordTextFormatter {

    private PlayerRecordTextFormatter() {
    }

    public static String getSmallTeamLogoUrl(String teamAbbr) {
        return (teamAbbr == null || teamAbbr.isEmpty()) ? "" : "https://static.nfl.com/static/content/public/static/wildcat/assets/img/logos/teams/" + teamAbbr + ".svg";
    }

    public static String getPlayerImageUrl(PlayerRecord playerRecord) {
        if (!isDefense(playerRecord.getPosition())) {
            return "http://s.nflcdn.com/static/content/public/static/img/fantasy/transparent/200x200/" + playerRecord.getEsbid() + ".png";
        } else {
            return "http://s.nflcdn.com/static/content/public/static/img/fantasy/transparent/200x200/" + playerRecord.getTeamAbbr() + ".png";
        }
    }

    private static boolean isDefense(String position) {
        return position.equals("DEF");
    }
}
