/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experiment.client.util;

import nl.mpi.tg.eg.experiment.client.model.UserData;
import nl.mpi.tg.eg.experiment.client.service.GroupScoreService;

/**
 * @since Jul 19, 2017 3:34:18 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class HtmlTokenFormatter {

    final GroupScoreService groupParticipantService;
    final UserData userData;

    public HtmlTokenFormatter(GroupScoreService groupParticipantService, UserData userData) {
        this.groupParticipantService = groupParticipantService;
        this.userData = userData;
    }

    public String formatString(String inputString) {
        String replacedTokensString = inputString;
        while (replacedTokensString.contains("</channelLoop>")) {
            final int channelLoopStart = replacedTokensString.indexOf("<channelLoop>");
            final int channelLoopEnd = replacedTokensString.indexOf("</channelLoop>");
            String channelLoopString = replacedTokensString.substring(channelLoopStart, channelLoopEnd + "</channelLoop>".length());
            System.out.println("channelLoopString:" + channelLoopString);
            String channelLoopStringOutput = "";
            for (String channel : groupParticipantService.getChannelScoreKeys()) {
                channelLoopStringOutput += channelLoopString.replaceAll("<channelLabel>", channel).replaceAll("<channelScore>", groupParticipantService.getChannelScore(channel)).replaceAll("<channelLoop>", "").replaceAll("</channelLoop>", "");
            }
            replacedTokensString = replacedTokensString.replace(channelLoopString, channelLoopStringOutput);
        }
        final String groupScore = groupParticipantService.getGroupScore();
        replacedTokensString = replacedTokensString.replace("<groupScore>", (groupScore != null) ? groupScore : "---");
        final String memberCode = groupParticipantService.getMemberCode();
        replacedTokensString = replacedTokensString.replace("<groupMemberCode>", (memberCode != null) ? memberCode : "---");
        final String allMemberCodes = groupParticipantService.getAllMemberCodes();
        final String activeChannel = groupParticipantService.getActiveChannel();
        replacedTokensString = replacedTokensString.replace("<groupAllMemberCodes>", (allMemberCodes != null) ? allMemberCodes : "---");
        replacedTokensString = replacedTokensString.replace("<groupOtherMemberCodes>", (allMemberCodes != null) ? allMemberCodes.replace(memberCode, "").replaceAll("[,]+", ",").replaceAll(",$", "").replaceAll("^,", "") : "---");
        replacedTokensString = replacedTokensString.replace("<channelOtherMemberCodes>", (activeChannel != null) ? activeChannel.replace(memberCode, "").replaceAll("[,]+", ",").replaceAll(",$", "").replaceAll("^,", "") : "---");
        replacedTokensString = replacedTokensString.replace("<groupActiveChannel>", (activeChannel != null) ? activeChannel : "---");
        final String groupCommunicationChannels = groupParticipantService.getGroupCommunicationChannels();
        replacedTokensString = replacedTokensString.replace("<groupCommunicationChannels>", (groupCommunicationChannels != null) ? groupCommunicationChannels : "---");
        final String messageString = groupParticipantService.getMessageString();
        replacedTokensString = replacedTokensString.replace("<groupMessageString>", (messageString != null) ? messageString : "---");
        final String groupId = groupParticipantService.getGroupId();
        replacedTokensString = replacedTokensString.replace("<groupId>", (groupId != null) ? groupId : "---");
        final String userLabel = groupParticipantService.getUserLabel();
        replacedTokensString = replacedTokensString.replace("<groupUserLabel>", (userLabel != null) ? userLabel : "---");
        final String channelScore = groupParticipantService.getChannelScore();
        replacedTokensString = replacedTokensString.replaceAll("<channelScore>", (channelScore != null) ? channelScore : "---");
        replacedTokensString = replacedTokensString.replaceAll("<playerScore>", Integer.toString(userData.getCurrentScore()));
        replacedTokensString = replacedTokensString.replaceAll("<playerBestScore>", Double.toString(userData.getBestScore()));
        return replacedTokensString;
    }
}
