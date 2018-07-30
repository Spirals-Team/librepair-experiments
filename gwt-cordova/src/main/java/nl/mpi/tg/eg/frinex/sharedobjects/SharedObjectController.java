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

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @since Oct 26, 2016 2:03:15 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Controller
public class SharedObjectController {

    private final static GroupManager GROUP_MANAGER = new GroupManager();
//    private String currentGroupId = null;

    @MessageMapping("/shared")
    @SendTo("/shared/animation")
    public SharedData getSharedData(SharedData sharedData) throws Exception {
        return sharedData;
    }

    @MessageMapping("/group")
    @SendTo("/shared/group")
    public GroupMessage getGroupData(GroupMessage groupMessage) throws Exception {
        final GroupMessage updateGroupData = updateGroupData(groupMessage);
        System.out.print("processMessage(instance,");
        System.out.print("\"" + groupMessage.getGroupId() + "\",");
        System.out.print("\"" + groupMessage.getScreenId() + "\",");
        System.out.print("\"" + groupMessage.getAllMemberCodes() + "\",");
        System.out.print("\"" + groupMessage.getGroupCommunicationChannels() + "\",");
        System.out.print("\"" + groupMessage.getMemberCode() + "\",");
        System.out.print("\"" + groupMessage.getOriginMemberCode() + "\",");
        System.out.print("\"" + groupMessage.getExpectedRespondents() + "\",");
        System.out.print("\"" + groupMessage.getActualRespondents() + "\",");
        System.out.print("" + groupMessage.getStimulusIndex() + ",");
        System.out.print("" + groupMessage.getOriginPhase() + ",");
        System.out.print("" + groupMessage.getRequestedPhase() + ",");
        System.out.print("\"" + groupMessage.getMessageString() + "\",");
        System.out.print("" + groupMessage.isGroupReady() + ",");
        System.out.print("" + groupMessage.getMemberScore() + ",");
        System.out.print("" + groupMessage.getChannelScore() + ",");
        System.out.print("" + groupMessage.getGroupScore() + ",");
        System.out.println("" + groupMessage.getEventMs() + ",");

        System.out.print("\"" + updateGroupData.getGroupId() + "\",");
        System.out.print("\"" + updateGroupData.getScreenId() + "\",");
        System.out.print("\"" + updateGroupData.getAllMemberCodes() + "\",");
        System.out.print("\"" + updateGroupData.getGroupCommunicationChannels() + "\",");
        System.out.print("\"" + updateGroupData.getMemberCode() + "\",");
        System.out.print("\"" + updateGroupData.getOriginMemberCode() + "\",");
        System.out.print("\"" + updateGroupData.getExpectedRespondents() + "\",");
        System.out.print("\"" + updateGroupData.getActualRespondents() + "\",");
        System.out.print("" + updateGroupData.getStimulusIndex() + ",");
        System.out.print("" + updateGroupData.getOriginPhase() + ",");
        System.out.print("" + updateGroupData.getRequestedPhase() + ",");
        System.out.print("\"" + updateGroupData.getMessageString() + "\",");
        System.out.print("" + updateGroupData.isGroupReady() + ",");
        System.out.print("" + updateGroupData.getMemberScore() + ",");
        System.out.print("" + updateGroupData.getChannelScore() + ",");
        System.out.print("" + updateGroupData.getGroupScore() + ",");
        System.out.println("" + updateGroupData.getEventMs() + ");//processMessage");
        return updateGroupData;
    }

    private synchronized GroupMessage updateGroupData(GroupMessage incomingMessage) {
        if (incomingMessage == null) {
            System.out.println("incomingMessage == null");
            return null;
        }
//        System.out.println("InMessage:" + incomingMessage.toString());
//        final String incomingStimuliList = incomingMessage.getStimuliList();
//        System.out.println("incomingMessage: ");
//        System.out.println(incomingMessage.getAllMemberCodes());
//        System.out.println(incomingMessage.getGroupCommunicationChannels());
//        System.out.println(incomingMessage.getGroupId());
//        System.out.println(incomingMessage.getGroupUUID());
//        System.out.println(incomingMessage.getMemberCode());
//        System.out.println(incomingMessage.getOriginMemberCode());
//        System.out.println(incomingMessage.getRequestedPhase());
//        System.out.println(incomingMessage.getActualRespondents());
//        System.out.println(incomingMessage.getExpectedRespondents());
//        System.out.println(incomingStimuliList);
//        System.out.println(incomingMessage.getStimulusId());
//        System.out.println(incomingMessage.getStimulusIndex());
//        System.out.println(incomingMessage.getUserId());
//        System.out.println(incomingMessage.getScreenId());
        if (!GROUP_MANAGER.isGroupMember(incomingMessage)) {
            if (!GROUP_MANAGER.addGroupMember(incomingMessage)) {
                return incomingMessage; // reject the request
            }
        }
        incomingMessage.setOriginMemberCode(incomingMessage.getMemberCode());
        GROUP_MANAGER.updateGroupData(incomingMessage);
        GROUP_MANAGER.updateResponderListForMessagePhase(incomingMessage);
        GROUP_MANAGER.updateGroupScore(incomingMessage);
        incomingMessage.setGroupReady(GROUP_MANAGER.isGroupReady(incomingMessage));
        // if the message is a reconnect request then send the last message for that chanel
        GroupMessage resendMessage = GROUP_MANAGER.updateChannelMessageIfOutOfDate(incomingMessage);
        if (resendMessage == null) {
            GROUP_MANAGER.setUsersLastMessage(incomingMessage);
//            System.out.println("OutMessage:" + incomingMessage.toString());
            return incomingMessage;
        } else {
            // if the message is a reconnect but the stimuli index is greater and the stimulus id == null then trigger an end of stimulus screen change
            if (incomingMessage.getStimulusIndex() > resendMessage.getStimulusIndex() && incomingMessage.getStimulusId() == null && incomingMessage.getExpectedRespondents().isEmpty()) {
                System.out.println("trigger an end of stimulus screen change:" + incomingMessage.toString());
//                incomingMessage.setExpectedRespondents(incomingMessage.getMemberCode().toString());
                return incomingMessage;
            } else {
//                System.out.println("ResendMessage:" + resendMessage.toString());
                return resendMessage;
            }
        }
    }
//    private void generateTestData(GroupMessage incomingMessage, GroupMessage outgoingMessage){
//        System.out.print("processMessage(instance,");
//            String userId,
//            String screenId,
//            String userLabel,
//            String groupId,
//            String allMemberCodes,
//            String communicationChannels,
//            String memberCode,
//            String originMemberCode,
//            String stimulusId,
//            String stimulusIndex,
//            String stimuliList,
//            String requestedPhase,
//            String messageString,
//            boolean groupReady,
//            String responseStimulusId,
//            String expectedRespondents,
//            String actualRespondents,
//            String groupUUID)
//    }
}
