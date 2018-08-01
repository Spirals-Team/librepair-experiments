/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics, Nijmegen
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import nl.mpi.tg.eg.experiment.client.model.UserId;

/**
 * @since Nov 18, 2016 2:53:20 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class GroupManager {

    private final HashMap<UserId, HashMap<GroupMessage, GroupMessage>> allMembersList = new HashMap<>();
    private final HashMap<GroupMessage, List<MemberCode>> allMemberCodes = new HashMap<>();
    private final HashMap<GroupMessage, HashMap<MemberCode, Integer>> groupScores = new HashMap<>();
    private final HashMap<GroupMessage, String> stimuliLists = new HashMap<>();
    private final HashMap<GroupId, GroupUUID> groupUUIDs = new HashMap<>();
    private final HashMap<GroupId, List<MemberCode>> unAllocatedMemberCodes = new HashMap<>();
    private final HashMap<GroupId, HashSet<UserId>> groupsMembers = new HashMap();
    private final HashMap<GroupMessage, HashMap<String, GroupMessage>> recentChannelMessages = new HashMap();
    GroupMessage lastGroupId = null;

    public boolean isGroupMember(GroupMessage groupMessage) {
        return groupsMembers.containsKey(groupMessage.getGroupId()) && groupsMembers.get(groupMessage.getGroupId()).contains(groupMessage.getUserId());
    }

    public void updateGroupData(GroupMessage groupMessage) {
        groupMessage.setUserLabel(groupMessage.getMemberCode() + " : " + groupMessage.getGroupId());
        groupMessage.setGroupUUID(groupUUIDs.get(groupMessage.getGroupId()));
        final String groupStimuliList = stimuliLists.get(groupMessage);
        if (groupStimuliList == null) {
            // keep the first stimuli list sent so that it can be set it into each subsequent message
            stimuliLists.put(groupMessage, groupMessage.getStimuliList());
        } else {
            groupMessage.setStimuliList(groupStimuliList);
        }
    }

    public boolean isGroupReady(GroupMessage groupMessage) {
        final List<MemberCode> membercodes = unAllocatedMemberCodes.get(groupMessage.getGroupId());
        if (membercodes == null) {
            return false;
        }
        return membercodes.isEmpty();
    }

    public void updateGroupScore(GroupMessage groupMessage) {
        if (!groupScores.containsKey(groupMessage)) {
            groupScores.put(groupMessage, new HashMap<MemberCode, Integer>());
        }
        final HashMap<MemberCode, Integer> groupScoresMap = groupScores.get(groupMessage);
        if (groupMessage.getMemberCode() != null && groupMessage.getMemberCode().isValid()) {
            groupScoresMap.put(groupMessage.getMemberCode(), groupMessage.getMemberScore());
        }
        int groupScore = 0;
        int groupChannelScore = 0;
        String channelMembers = "";
        for (String channel : groupMessage.getGroupCommunicationChannels().split("\\|")) // check if the communication channel applies to this group member
        {
            if (groupMessage.getMemberCode().memberOfChannel(channel)) {
                channelMembers += channel;
            }
        }
        for (MemberCode currentMember : groupScoresMap.keySet()) {
            if (currentMember.memberOfChannel(channelMembers)) {
                Integer currentScore = groupScoresMap.get(currentMember);
                groupChannelScore += currentScore;
            }
        }
        for (GroupMessage groupMessageKey : groupScores.keySet()) {
            if (groupMessageKey.getGroupUUID().equals(groupMessage.getGroupUUID())) {
                final HashMap<MemberCode, Integer> groupScoresAllRoundsMap = groupScores.get(groupMessageKey);
                for (MemberCode currentMember : groupScoresAllRoundsMap.keySet()) {
                    Integer currentScore = groupScoresAllRoundsMap.get(currentMember);
                    groupScore += currentScore;
                }
            }
        }
        groupMessage.setGroupScore(groupScore);
        groupMessage.setChannelScore(groupChannelScore);
    }

    public GroupMessage updateChannelMessageIfOutOfDate(final GroupMessage incomingMessage) {
        GroupMessage mostRecentChannelMessage = incomingMessage;
        boolean resendingOldMessage = false;
        // keep the member id and the channel data before updating the message to the most recent seen by the server, if found
        final String groupCommunicationChannels = incomingMessage.getGroupCommunicationChannels();
        System.out.println("groupCommunicationChannels: " + groupCommunicationChannels);
        System.out.println("memberCode: " + incomingMessage.getMemberCode());
        for (String channel : groupCommunicationChannels.split("\\|")) // check if the communication channel applies to this group member
        {
            System.out.println("channel: " + channel);
            if (incomingMessage.getMemberCode().memberOfChannel(channel)) {
                System.out.println("is channel member");
                if (recentChannelMessages.containsKey(incomingMessage)) {
                    final GroupMessage channelLastMessage = recentChannelMessages.get(incomingMessage).get(channel);
                    if (channelLastMessage != null) {
                        System.out.println("currentMemberCode: " + channelLastMessage.getMemberCode());
                        System.out.println("is common member");
                        System.out.println("mostRecentChannelMessage.getRequestedPhase():" + mostRecentChannelMessage.getRequestedPhase());
                        System.out.println("membersLastMessage.getRequestedPhase():" + channelLastMessage.getRequestedPhase());
                        if (mostRecentChannelMessage.getRequestedPhase() < channelLastMessage.getRequestedPhase()) {
                            System.out.println("other is more advanced than sent");
                            // select only the most recent message for any user in this channel
                            System.out.println("membersLastMessage.getExpectedRespondents(): " + channelLastMessage.getExpectedRespondents());
                            System.out.println("membersLastMessage.getActualRespondents(): " + channelLastMessage.getActualRespondents());
                            // expected respondants list should have out of channel respondents omitted for this comparison
                            System.out.println("all ExpectedRespondents replied");
                            // only resend a message if all expected respondants have replied                                        
                            mostRecentChannelMessage = channelLastMessage;
                            resendingOldMessage = true;
                        }
                    }
                }
            }
        }
        if (resendingOldMessage) {
            System.out.println("resendingOldMessage");
            GroupMessage resendMessage = new GroupMessage();
            // preserve the user id and member code of the requesting participant, even if the message is a resend from a different participant
            resendMessage.setActualRespondents(mostRecentChannelMessage.getActualRespondents());
            resendMessage.setAllMemberCodes(mostRecentChannelMessage.getAllMemberCodes());
            resendMessage.setChannelScore(mostRecentChannelMessage.getChannelScore());
            resendMessage.setEventMs(mostRecentChannelMessage.getEventMs());
            resendMessage.setExpectedRespondents(mostRecentChannelMessage.getExpectedRespondents());
            resendMessage.setGroupCommunicationChannels(mostRecentChannelMessage.getGroupCommunicationChannels());
            resendMessage.setGroupId(mostRecentChannelMessage.getGroupId());
            resendMessage.setGroupReady(mostRecentChannelMessage.isGroupReady());
            resendMessage.setGroupUUID(mostRecentChannelMessage.getGroupUUID());
            resendMessage.setGroupScore(mostRecentChannelMessage.getGroupScore());
            resendMessage.setMessageString(mostRecentChannelMessage.getMessageString());
            resendMessage.setOriginMemberCode(mostRecentChannelMessage.getMemberCode());
            resendMessage.setOriginPhase(mostRecentChannelMessage.getOriginPhase());
            resendMessage.setRequestedPhase(mostRecentChannelMessage.getRequestedPhase());
            resendMessage.setResponseStimulusId(mostRecentChannelMessage.getResponseStimulusId());
            resendMessage.setResponseStimulusOptions(mostRecentChannelMessage.getResponseStimulusOptions());
            resendMessage.setStimulusId(mostRecentChannelMessage.getStimulusId());
            resendMessage.setStimulusIndex(mostRecentChannelMessage.getStimulusIndex());
            resendMessage.setStimuliList(mostRecentChannelMessage.getStimuliList());
            resendMessage.setScreenId(mostRecentChannelMessage.getScreenId());

            resendMessage.setMemberCode(incomingMessage.getMemberCode());
            resendMessage.setUserId(incomingMessage.getUserId());
            resendMessage.setUserLabel(incomingMessage.getUserLabel());
            resendMessage.setOriginMemberCode(mostRecentChannelMessage.getMemberCode());
            return resendMessage;
        } else {
            return null;
        }
    }

    public boolean addGroupMember(GroupMessage groupMessage) {
        boolean memberAdded = false;
        if (!unAllocatedMemberCodes.containsKey(groupMessage.getGroupId())) {
            groupsMembers.put(groupMessage.getGroupId(), new HashSet<UserId>());
            allMemberCodes.put(groupMessage, MemberCode.fromAllMemberCodes(groupMessage.getAllMemberCodes()));
            unAllocatedMemberCodes.put(groupMessage.getGroupId(), MemberCode.fromAllMemberCodes(groupMessage.getAllMemberCodes()));
            // keeping a UUID for each group could help disambiguate when the server is restarted and the same group name reused
            groupUUIDs.put(groupMessage.getGroupId(), new GroupUUID(groupMessage.getGroupId(), UUID.randomUUID().toString()));
        }
        final List<MemberCode> availableMemberCodes = unAllocatedMemberCodes.get(groupMessage.getGroupId());
        // even if the message has the wrong stimuli list, the participant will still be added to the group, but the correct stimili list will be returned so as to trigger the client to reload its stimili.
        if (groupMessage.getMemberCode() != null
                && groupMessage.getMemberCode().isValid()
                && availableMemberCodes.contains(groupMessage.getMemberCode())) {
            // if the member code is provided and it is available then allocate it
            availableMemberCodes.remove(groupMessage.getMemberCode());
//        } else if (!availableMemberCodes.isEmpty()) {
//            groupMessage.setMemberCode(availableMemberCodes.remove(0));
            groupsMembers.get(groupMessage.getGroupId()).add(groupMessage.getUserId());
            memberAdded = true;
        }
        System.out.println("groupMessage: ");
        System.out.println(groupMessage.getAllMemberCodes());
        System.out.println(groupMessage.getGroupCommunicationChannels());
        System.out.println(groupMessage.getGroupId());
        System.out.println(groupMessage.getGroupUUID());
        System.out.println(groupMessage.getMemberCode());
        System.out.println(groupMessage.getOriginMemberCode());
        System.out.println(groupMessage.getRequestedPhase());
        System.out.println(groupMessage.getExpectedRespondents());
        System.out.println(groupMessage.getStimuliList());
        System.out.println(groupMessage.getStimulusId());
        System.out.println(groupMessage.getStimulusIndex());
        System.out.println(groupMessage.getUserId());
        System.out.println(groupMessage.getScreenId());
        return memberAdded;
    }

    public void updateResponderListForMessagePhase(GroupMessage storedMessage) {
        final Set<MemberCode> respondingMemberCodes = new HashSet<>();
        if (storedMessage.getOriginMemberCode() != null) {
            respondingMemberCodes.add(storedMessage.getOriginMemberCode());
        }
        for (final HashMap<GroupMessage, GroupMessage> userGroups : allMembersList.values()) {
            GroupMessage lastGroupMessage = userGroups.get(storedMessage);
            if (lastGroupMessage != null && lastGroupMessage.getOriginMemberCode() != null) {
//          if the group matches
                if (storedMessage.equals(lastGroupMessage)) {
//                    // this is the same or later phase
//                    if (storedMessage.getRequestedPhase().compareTo(lastGroupMessage.getRequestedPhase()) <= 0) {
                    // this is the same phase
                    if (storedMessage.getRequestedPhase().equals(lastGroupMessage.getRequestedPhase())) {
                        respondingMemberCodes.add(lastGroupMessage.getOriginMemberCode());
                    }
                }
            }
        }
        List<MemberCode> sortedRespondingMemberCodes = new ArrayList(respondingMemberCodes);
        Collections.sort(sortedRespondingMemberCodes);
        String respondingMemberCodesString = "";
        for (MemberCode memberCode : sortedRespondingMemberCodes) {
            if (!respondingMemberCodesString.isEmpty()) {
                respondingMemberCodesString += ",";
            }
            respondingMemberCodesString += memberCode.toString();
        }
        storedMessage.setActualRespondents(respondingMemberCodesString);
        for (final HashMap<GroupMessage, GroupMessage> userGroups : allMembersList.values()) {
            GroupMessage lastGroupMessage = userGroups.get(storedMessage);
//          if the group matches
            if (storedMessage.equals(lastGroupMessage)) {
                // this is the same phase
                if (storedMessage.getRequestedPhase().equals(lastGroupMessage.getRequestedPhase())) {
                    lastGroupMessage.setActualRespondents(respondingMemberCodesString);
                }
            }
        }
        if (storedMessage.getGroupUUID() != null) /* check that the message is from a group member */ {
            for (String channel : storedMessage.getGroupCommunicationChannels().split("\\|")) // check if the communication channel applies to this group member
            {
                System.out.println("channel: " + channel);
                System.out.println("MemberCode: " + storedMessage.getMemberCode());
                System.out.println("RequestedPhase: " + storedMessage.getRequestedPhase());
                System.out.println("ActualRespondents: " + storedMessage.getActualRespondents());
                System.out.println("ExpectedRespondents: " + storedMessage.getExpectedRespondents());
                if (storedMessage.getMemberCode().memberOfChannel(channel)) {
                    System.out.println("memberOfChannel");
                    if (storedMessage.haveAllRespondended(channel)) {
                        System.out.println("AllRespondended");
                        HashMap<String, GroupMessage> groupCompleteMessages = recentChannelMessages.get(storedMessage);
                        if (groupCompleteMessages == null) {
                            groupCompleteMessages = new HashMap<>();
                            recentChannelMessages.put(storedMessage, groupCompleteMessages);
                        }
                        final GroupMessage previousMessage = groupCompleteMessages.get(channel);
                        if (previousMessage == null || previousMessage.getRequestedPhase() <= storedMessage.getRequestedPhase()) {
                            groupCompleteMessages.put(channel, storedMessage);
                        }
                    }
                }
            }
        }
    }

    public void setUsersLastMessage(GroupMessage storedMessage) {
        if (!allMembersList.containsKey(storedMessage.getUserId())) {
            allMembersList.put(storedMessage.getUserId(), new HashMap<GroupMessage, GroupMessage>());
        }
        allMembersList.get(storedMessage.getUserId()).put(storedMessage, storedMessage);
    }

    public GroupMessage getGroupMember(GroupMessage incomingMessage) {
        HashMap<GroupMessage, GroupMessage> userGroups = allMembersList.get(incomingMessage.getUserId());
        if (userGroups != null) {
            return userGroups.get(incomingMessage);
        } else {
            return null;
        }
    }
}
