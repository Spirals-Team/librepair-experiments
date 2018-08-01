/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.frinex.sharedobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import nl.mpi.tg.eg.experiment.client.exception.UserIdException;
import nl.mpi.tg.eg.experiment.client.model.UserId;

/**
 * @since Oct 27, 2016 4:03:41 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class GroupMessage {

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.groupId);
        hash = 59 * hash + Objects.hashCode(this.screenId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GroupMessage other = (GroupMessage) obj;
        if (!Objects.equals(this.groupId, other.groupId)) {
            return false;
        }
        if (!Objects.equals(this.screenId, other.screenId)) {
            return false;
        }
        return true;
    }

    private UserId userId;
    private GroupId groupId;
    private GroupUUID groupUUID;
    private String screenId;
    private String userLabel;
    private String allMemberCodes;
    private String groupCommunicationChannels;
    private MemberCode memberCode;
    private MemberCode originMemberCode;
    private String stimulusId;
    private String responseStimulusOptions;
    private String responseStimulusId;
    private String expectedRespondents;
    private String actualRespondents;
    private Integer stimulusIndex;
    private String stimuliList;
    private Integer originPhase;
    private Integer requestedPhase;
    private String messageString;
    private boolean groupReady;
    private int memberScore;
    private int channelScore;
    private int groupScore;
    private int eventMs;

    public GroupMessage() {
    }

    public GroupMessage(String groupId, String screenId, String userId, String memberCode) throws UserIdException {
        this.groupId = new GroupId(groupId);
        this.screenId = screenId;
        this.userId = new UserId(userId);
        this.memberCode = new MemberCode(memberCode);
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public GroupId getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupId groupId) {
        this.groupId = groupId;
    }

    public GroupUUID getGroupUUID() {
        return groupUUID;
    }

    public void setGroupUUID(GroupUUID groupUUID) {
        this.groupUUID = groupUUID;
    }

    public String getResponseStimulusOptions() {
        return responseStimulusOptions;
    }

    public void setResponseStimulusOptions(String responseStimulusOptions) {
        this.responseStimulusOptions = responseStimulusOptions;
    }

    public String getResponseStimulusId() {
        return responseStimulusId;
    }

    public void setResponseStimulusId(String responseStimulusId) {
        this.responseStimulusId = responseStimulusId;
    }

    public String getExpectedRespondents() {
        return expectedRespondents;
    }

    public void setExpectedRespondents(String expectedRespondents) {
        this.expectedRespondents = expectedRespondents;
    }

    public String getActualRespondents() {
        return actualRespondents;
    }

    public void setActualRespondents(String actualRespondents) {
        this.actualRespondents = actualRespondents;
    }

    public boolean haveAllRespondended(String channel) {
        if (expectedRespondents == null || expectedRespondents.isEmpty()) {
            return true;
        }
        if (actualRespondents == null) {
            return false;
        }
        final List<String> expectedRespondentsList = new ArrayList(Arrays.asList(expectedRespondents.split(",")));
        final List<String> actualRespondentsList = Arrays.asList(actualRespondents.split(","));
        final List<String> channelList = Arrays.asList(channel.split(","));
        expectedRespondentsList.retainAll(channelList);
        expectedRespondentsList.removeAll(actualRespondentsList);
        return expectedRespondentsList.isEmpty();
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public String getAllMemberCodes() {
        return allMemberCodes;
    }

    public void setAllMemberCodes(String allMemberCodes) {
        this.allMemberCodes = allMemberCodes;
    }

    public String getGroupCommunicationChannels() {
        return groupCommunicationChannels;
    }

    public void setGroupCommunicationChannels(String groupCommunicationChannels) {
        this.groupCommunicationChannels = groupCommunicationChannels;
    }

    public MemberCode getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(MemberCode memberCode) {
        this.memberCode = memberCode;
    }

    public MemberCode getOriginMemberCode() {
        return originMemberCode;
    }

    public void setOriginMemberCode(MemberCode originMemberCode) {
        this.originMemberCode = originMemberCode;
    }

    public String getStimulusId() {
        return stimulusId;
    }

    public void setStimulusId(String stimulusId) {
        this.stimulusId = stimulusId;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    public boolean isGroupReady() {
        return groupReady;
    }

    public void setGroupReady(boolean groupReady) {
        this.groupReady = groupReady;
    }

    public Integer getStimulusIndex() {
        return stimulusIndex;
    }

    public void setStimulusIndex(Integer stimulusIndex) {
        this.stimulusIndex = stimulusIndex;
    }

    public String getStimuliList() {
        return stimuliList;
    }

    public void setStimuliList(String stimuliList) {
        this.stimuliList = stimuliList;
    }

    public Integer getOriginPhase() {
        return originPhase;
    }

    public void setOriginPhase(Integer originPhase) {
        this.originPhase = originPhase;
    }

    public Integer getRequestedPhase() {
        return requestedPhase;
    }

    public void setRequestedPhase(Integer requestedPhase) {
        this.requestedPhase = requestedPhase;
    }

    public int getMemberScore() {
        return memberScore;
    }

    public void setMemberScore(int memberScore) {
        this.memberScore = memberScore;
    }

    public int getGroupScore() {
        return groupScore;
    }

    public void setGroupScore(int groupScore) {
        this.groupScore = groupScore;
    }

    public int getChannelScore() {
        return channelScore;
    }

    public void setChannelScore(int channelScore) {
        this.channelScore = channelScore;
    }

    public int getEventMs() {
        return eventMs;
    }

    public void setEventMs(int eventMs) {
        this.eventMs = eventMs;
    }

    @Override
    public String toString() {
        return "GroupMessage{" + "groupId=" + groupId + ", screenId=" + screenId + ", allMemberCodes=" + allMemberCodes + ", groupCommunicationChannels=" + groupCommunicationChannels + ", memberCode=" + memberCode + ", originMemberCode=" + originMemberCode + ", expectedRespondents=" + expectedRespondents + ", actualRespondents=" + actualRespondents + ", stimulusIndex=" + stimulusIndex + ", originPhase=" + originPhase + ", requestedPhase=" + requestedPhase + ", messageString=" + messageString + ", groupReady=" + groupReady + ", memberScore=" + memberScore + ", channelScore=" + channelScore + ", groupScore=" + groupScore + ", eventMs=" + eventMs + '}';
    }
}
