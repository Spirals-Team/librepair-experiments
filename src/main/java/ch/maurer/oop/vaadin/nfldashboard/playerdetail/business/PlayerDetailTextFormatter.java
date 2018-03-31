package ch.maurer.oop.vaadin.nfldashboard.playerdetail.business;

import ch.maurer.oop.vaadin.nfldashboard.common.business.TeamNameService;

public final class PlayerDetailTextFormatter {

    private PlayerDetailTextFormatter() {
    }

    public static String getInjuryGameStatus(String injuryGameStatus) {
        if (injuryGameStatus == null) {
            return "";
        } else if (isInjuredReserve(injuryGameStatus)) {
            return "IR";
        } else if (isLimitedParticipationInPractice(injuryGameStatus)) {
            return "LP";
        } else if (isFullParticipationInPractice(injuryGameStatus)) {
            return "FP";
        } else if (isDidNotParticipateInPractice(injuryGameStatus)) {
            return "DNP";
        }
        return injuryGameStatus;
    }

    private static boolean isInjuredReserve(String injuryGameStatus) {
        return injuryGameStatus.equals("Injured Reserve");
    }

    private static boolean isLimitedParticipationInPractice(String injuryGameStatus) {
        return injuryGameStatus.equals("Limited Participation in Practice");
    }

    private static boolean isFullParticipationInPractice(String injuryGameStatus) {
        return injuryGameStatus.equals("Full Participation in Practice");
    }

    private static boolean isDidNotParticipateInPractice(String injuryGameStatus) {
        return injuryGameStatus.equals("Did Not Participate In Practice");
    }

    public static String getJerseyNumberWithNumberSign(String jerseyNumber) {
        return jerseyNumber.isEmpty() ? "" : "#" + jerseyNumber;
    }

    public static String getTeamNameFull(String teamAbbr) {
        return TeamNameService.getTeamNameFull(teamAbbr);
    }

}
